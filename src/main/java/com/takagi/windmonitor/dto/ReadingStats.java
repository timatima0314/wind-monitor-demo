package com.takagi.windmonitor.dto;

public class ReadingStats {

    private final int count;
    private final MetricStats windSpeed;
    private final MetricStats rpm;
    private final MetricStats temperature;

    public ReadingStats(int count, MetricStats windSpeed, MetricStats rpm, MetricStats temperature) {
        this.count = count;
        this.windSpeed = windSpeed;
        this.rpm = rpm;
        this.temperature = temperature;
    }

    public int getCount() {
        return count;
    }

    public MetricStats getWindSpeed() {
        return windSpeed;
    }

    public MetricStats getRpm() {
        return rpm;
    }

    public MetricStats getTemperature() {
        return temperature;
    }
}