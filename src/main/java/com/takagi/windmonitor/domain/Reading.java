package com.takagi.windmonitor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;

@Entity
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant measuredAt; //タイム
    private double windSpeed; // 風速
    private double rpm; // 回転数
    private double temperature; // 温度
    private String status; // "OK" or "WARNING"

    // デフォルトコンストラクタ（JPA用）
    protected Reading() {
    }

    public Reading(Instant measuredAt, double windSpeed, double rpm,
            double temperature, String status) {
        this.measuredAt = measuredAt;
        this.windSpeed = windSpeed;
        this.rpm = rpm;
        this.temperature = temperature;
        this.status = status;
    }

    // getter のみで十分（学習用）
    public Long getId() {
        return id;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getRpm() {
        return rpm;
    }

    public double getTemperature() {
        return temperature;
    }

    public String getStatus() {
        return status;
    }
}
