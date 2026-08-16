package com.takagi.windmonitor.mqtt;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import java.util.concurrent.ThreadLocalRandom;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MqttSimulatorPublisher {

    private final boolean enabled;
    private final String brokerUrl;
    private final String clientId;
    private final String topic;
    private final long intervalMs;

    private MqttClient client;
    private Thread worker;

    public MqttSimulatorPublisher(
            @Value("${mqtt.simulator.enabled:false}") boolean enabled,
            @Value("${mqtt.broker-url}") String brokerUrl,
            @Value("${mqtt.simulator.client-id}") String clientId,
            @Value("${mqtt.topic}") String topic,
            @Value("${mqtt.simulator.interval-ms:5000}") long intervalMs) {
        this.enabled = enabled;
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.topic = topic;
        this.intervalMs = intervalMs;
    }

    @PostConstruct
    public void start() {
        if (!enabled) {
            System.out.println("MQTT simulator disabled");
            return;
        }

        worker = new Thread(this::loop, "mqtt-simulator");
        worker.setDaemon(true);
        worker.start();
    }

    private void loop() {
        while (true) {
            try {
                ensureConnected();
                String payload = buildPayload();
                client.publish(topic, new MqttMessage(payload.getBytes()));
                System.out.println("MQTT simulator published: " + payload);
                Thread.sleep(intervalMs);
            } catch (InterruptedException e) {
                break;
            } catch (Exception e) {
                System.out.println("MQTT simulator error: " + e.getMessage());
                try {
                    Thread.sleep(intervalMs);
                } catch (InterruptedException ie) {
                    break;
                }
            }
        }
    }

    private void ensureConnected() throws MqttException {
        if (client == null) {
            client = new MqttClient(brokerUrl, clientId);
        }
        if (!client.isConnected()) {
            client.connect();
            System.out.println("MQTT simulator connected");
        }
    }

    private String buildPayload() {
        double windSpeed = ThreadLocalRandom.current().nextDouble(0, 30);
        double rpm = ThreadLocalRandom.current().nextDouble(0, 20);
        double temperature = ThreadLocalRandom.current().nextDouble(20, 100);
        // 検証に通る範囲の乱数（必要ならあとで調整）
        return String.format(
                "{\"windSpeed\":%.2f,\"rpm\":%.2f,\"temperature\":%.2f}",
                windSpeed, rpm, temperature);
    }

    @PreDestroy
    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
        }
    }
}