package com.takagi.windmonitor.scheduler;

import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.service.ReadingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReadingScheduler {

    private final ReadingService readingService;

    public ReadingScheduler(ReadingService readingService) {
        this.readingService = readingService;
    }

    @Scheduled(fixedRate = 5000)
    public void tick() {
        Reading reading = readingService.generateAndSave();
        System.out.println(
                "saved id=" + reading.getId()
                        + " status=" + reading.getStatus()
                        + " wind=" + reading.getWindSpeed());
    }
}
