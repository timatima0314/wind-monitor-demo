package com.takagi.windmonitor.mqtt;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import tools.jackson.databind.ObjectMapper;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.service.ReadingService;

@Component
public class MqttSubscriber {

    private final String brokerUrl;
    private final String clientId;
    private final String topic;

    private MqttClient client;
    private ReadingService readingService;
    private ObjectMapper objectMapper;

    public MqttSubscriber(
            @Value("${mqtt.broker-url}") String brokerUrl,
            @Value("${mqtt.client-id}") String clientId,
            @Value("${mqtt.topic}") String topic,
            ReadingService readingService, // ← 追加
            ObjectMapper objectMapper) { // ← これが「ObjectMapper をコンストラクタ注入」
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.topic = topic;
        this.readingService = readingService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void start() throws MqttException {
        client = new MqttClient(brokerUrl, clientId);
        client.connect();
        client.subscribe(topic, (t, message) -> {
            String payload = new String(message.getPayload());
            try {
                var node = objectMapper.readTree(payload);
                System.out.println("payload" + payload);

                double windSpeed = node.get("windSpeed").asDouble();
                double rpm = node.get("rpm").asDouble();
                double temperature = node.get("temperature").asDouble();

                Reading reading = readingService.saveMeasured(windSpeed, rpm, temperature);
                System.out.println(
                        "MQTT saved id=" + reading.getId()
                                + " status=" + reading.getStatus());
            } catch (Exception e) {
                System.out.println("MQTT parse/save failed: " + e.getMessage());
            }
        });
    }

    @PreDestroy
    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
        }
    }
}