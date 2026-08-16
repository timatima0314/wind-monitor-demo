package com.takagi.windmonitor.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.Instant;

@Entity
public class AlarmEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Instant occurredAt;
    private String type; // "RAISED" or "CLEARED"
    private String message;

    protected AlarmEvent() {
    }

    public AlarmEvent(Instant occurredAt, String type, String message) {
        this.occurredAt = occurredAt;
        this.type = type;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public String getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}