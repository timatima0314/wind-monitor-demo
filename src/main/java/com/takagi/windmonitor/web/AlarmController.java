package com.takagi.windmonitor.web;

import com.takagi.windmonitor.domain.AlarmEvent;
import com.takagi.windmonitor.repository.AlarmEventRepository;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/alarms")
public class AlarmController {

    private final AlarmEventRepository alarmEventRepository;

    public AlarmController(AlarmEventRepository alarmEventRepository) {
        this.alarmEventRepository = alarmEventRepository;
    }

    @GetMapping
    public List<AlarmEvent> recent() {
        return alarmEventRepository.findTop20ByOrderByOccurredAtDesc();
    }
}