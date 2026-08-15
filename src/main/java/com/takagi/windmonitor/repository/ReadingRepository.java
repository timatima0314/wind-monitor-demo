package com.takagi.windmonitor.repository;

import com.takagi.windmonitor.domain.Reading;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReadingRepository extends JpaRepository<Reading, Long> {

    Optional<Reading> findTopByOrderByMeasuredAtDesc();

    List<Reading> findTop10ByOrderByMeasuredAtDesc();
}
