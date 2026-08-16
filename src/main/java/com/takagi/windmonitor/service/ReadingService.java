package com.takagi.windmonitor.service;

import com.takagi.windmonitor.domain.AlarmEvent;
import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.repository.AlarmEventRepository;
import com.takagi.windmonitor.repository.ReadingRepository;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ReadingService {

    private final ReadingRepository repository;
    private final AlarmEventRepository alarmEventRepository;
    private final double warnWindSpeed;
    private final double warnTemperature;

    public ReadingService(
            ReadingRepository repository,
            AlarmEventRepository alarmEventRepository,
            @Value("${reading.warn.wind-speed}") double warnWindSpeed,
            @Value("${reading.warn.temperature}") double warnTemperature) {
        this.repository = repository;
        this.alarmEventRepository = alarmEventRepository;
        this.warnWindSpeed = warnWindSpeed;
        this.warnTemperature = warnTemperature;
    }

    /** MQTT など外部から来た計測値を判定して保存する */
    public Reading saveMeasured(double windSpeed, double rpm, double temperature) {
        String previousStatus = repository.findTopByOrderByMeasuredAtDesc()
                .map(Reading::getStatus) //中身があれば、その status（"OK" / "WARNING"）だけ取り出す
                .orElse("OK"); //まだ1件も無い（起動直後）なら、前回は "OK" だったことにする

        String status = judge(windSpeed, temperature);
        Reading reading = new Reading(
                Instant.now(),
                windSpeed,
                rpm,
                temperature,
                status);
        Reading saved = repository.save(reading);

        if (!previousStatus.equals(status)) {//文字列どうしの中身比較は equals
            String type = "WARNING".equals(status) ? "RAISED" : "CLEARED";
            String message = "status " + previousStatus + " -> " + status
                    + " (wind=" + windSpeed + ", temp=" + temperature + ")";
            alarmEventRepository.save(new AlarmEvent(Instant.now(), type, message));
            System.out.println("Alarm " + type + ": " + message);
        }
        return saved;
    }

    /** 閾値判定 */
    private String judge(double windSpeed, double temperature) {
        if (windSpeed > warnWindSpeed || temperature > warnTemperature) {
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
