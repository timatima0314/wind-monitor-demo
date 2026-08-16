package com.takagi.windmonitor.dto;

public class MetricStats {

    private final double avg;
    private final double min;
    private final double max;

    public MetricStats(double avg, double min, double max) {
        this.avg = avg;
        this.min = min;
        this.max = max;
    }

    public double getAvg() {
        return avg;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }
}