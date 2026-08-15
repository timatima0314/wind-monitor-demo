package com.takagi.windmonitor.web;

import com.takagi.windmonitor.domain.Reading;
import com.takagi.windmonitor.service.ReadingService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    /** 直近10件 */
    @GetMapping
    public List<Reading> recent() {
        return readingService.findRecent();
    }
}
