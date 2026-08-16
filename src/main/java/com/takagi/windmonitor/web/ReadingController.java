package com.takagi.windmonitor.web;

import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.dto.ReadingStats;
import com.takagi.windmonitor.service.ReadingService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/readings")
public class ReadingController {

    private final ReadingService readingService;

    public ReadingController(ReadingService readingService) {
        this.readingService = readingService;
    }

    /** 最新1件 */
    @GetMapping("/latest")
    public ResponseEntity<Reading> latest() {
        return readingService.findLatest()
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/stats")
    public ReadingStats stats(@RequestParam(defaultValue = "10") int limit) {
        int size = Math.min(Math.max(limit, 1), 100);
        return readingService.stats(size);
    }

    @GetMapping
    public List<Reading> recent(
            @RequestParam(defaultValue = "10") int limit) {
        int size = Math.min(Math.max(limit, 1), 100); // 1〜100 に制限
        return readingService.findRecent(size);
    }
}
