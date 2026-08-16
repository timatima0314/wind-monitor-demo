package com.takagi.windmonitor.repository;

import com.takagi.windmonitor.domain.AlarmEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmEventRepository extends JpaRepository<AlarmEvent, Long> {

    List<AlarmEvent> findTop20ByOrderByOccurredAtDesc();
}