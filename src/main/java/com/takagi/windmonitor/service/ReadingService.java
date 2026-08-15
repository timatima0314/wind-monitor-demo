package com.takagi.windmonitor.service;

import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.repository.ReadingRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ReadingService {

    private final ReadingRepository repository;

    // コンストラクタ注入（DI）
    public ReadingService(ReadingRepository repository) {
        this.repository = repository;
    }

    /** MQTT など外部から来た計測値を判定して保存する */
    public Reading saveMeasured(double windSpeed, double rpm, double temperature) {
        String status = judge(windSpeed, temperature);
        Reading reading = new Reading(
                Instant.now(),
                windSpeed,
                rpm,
                temperature,
                status);
        return repository.save(reading);
    }

    /** 閾値判定 */
    private String judge(double windSpeed, double temperature) {
        if (windSpeed > 20 || temperature > 80) {
            return "WARNING";
        }
        return "OK";
    }

    public Optional<Reading> findLatest() {
        return repository.findTopByOrderByMeasuredAtDesc();
    }

    public List<Reading> findRecent() {
        return repository.findTop10ByOrderByMeasuredAtDesc();
    }
}
