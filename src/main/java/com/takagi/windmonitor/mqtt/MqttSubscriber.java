package com.takagi.windmonitor.mqtt;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.Duration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
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
    private final long reconnectDelayMs;
    private final long offlineAfterMs;
    private volatile Instant lastReceivedAt; // 有効受信のたびに更新。最初は null

    public MqttSubscriber(
            @Value("${mqtt.broker-url}") String brokerUrl,
            @Value("${mqtt.client-id}") String clientId,
            @Value("${mqtt.topic}") String topic,
            @Value("${mqtt.reconnect-delay-ms}") long reconnectDelayMs,
            @Value("${mqtt.offline-after-ms}") long offlineAfterMs,
            ReadingService readingService,
            ObjectMapper objectMapper) {
        this.brokerUrl = brokerUrl;
        this.clientId = clientId;
        this.topic = topic;
        this.readingService = readingService;
        this.objectMapper = objectMapper;
        this.reconnectDelayMs = reconnectDelayMs;
        this.offlineAfterMs = offlineAfterMs;
    }

    @PostConstruct
    public void start() {

        tryConnect();
        startReconnectLoop();
    }

    private void tryConnect() {
        try {
            if (client == null) {
                client = new MqttClient(brokerUrl, clientId);
            }
            if (!client.isConnected()) {
                client.connect();
                client.subscribe(topic, (t, message) -> {
                    String payload = new String(message.getPayload());
                    try {
                        var node = objectMapper.readTree(payload);
                        System.out.println("payload" + payload);

                        if (node.get("windSpeed") == null
                                || node.get("rpm") == null
                                || node.get("temperature") == null) {
                            System.out.println("MQTT invalid payload (missing field): " + payload);
                            return;
                        }

                        double windSpeed = node.get("windSpeed").asDouble();
                        double rpm = node.get("rpm").asDouble();
                        double temperature = node.get("temperature").asDouble();

                        if (windSpeed < 0 || windSpeed > 60
                                || rpm < 0 || rpm > 50
                                || temperature < -20 || temperature > 150) {
                            System.out.println("MQTT invalid payload (out of range): " + payload);
                            return;
                        }
                        Reading reading = readingService.saveMeasured(windSpeed, rpm, temperature);
                        System.out.println(
                                "MQTT saved id=" + reading.getId()
                                        + " status=" + reading.getStatus());
                        lastReceivedAt = Instant.now();
                    } catch (Exception e) {
                        System.out.println("MQTT parse/save failed: " + e.getMessage());
                    }

                });
                System.out.println("MQTT subscribed to " + topic + "接続中");
            }
        } catch (MqttException e) {
            System.out.println("MQTT connect failed, will retry: " + e.getMessage());
        }
    }

    private void startReconnectLoop() {
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(reconnectDelayMs);
                    if (client == null || !client.isConnected()) {
                        System.out.println("MQTT retrying connect...");
                        tryConnect();
                    }
                    Instant last = lastReceivedAt;
                    if (last != null) {
                        long silentMs = Duration.between(last, Instant.now()).toMillis();
                        if (silentMs > offlineAfterMs) {
                            System.out.println("MQTT data offline (no message for " + silentMs + " ms)");
                        }
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }, "mqtt-reconnect");
        t.setDaemon(true); // アプリ終了を邪魔しない
        t.start();

    }

    @PreDestroy
    public void stop() throws MqttException {
        if (client != null && client.isConnected()) {
            client.disconnect();
            client.close();
        }
    }
}