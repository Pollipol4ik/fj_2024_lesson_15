package org.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class MetricsController {

    private final Counter customMetricCounter;

    public MetricsController(MeterRegistry meterRegistry) {
        this.customMetricCounter = meterRegistry.counter("custom_requests_total", "endpoint", "/custom");
    }

    @GetMapping("/log")
    public ResponseEntity<String> logTestRequest(@RequestHeader(value = "User-ID", required = false) String userId) {
        logRequestWithUserId(userId, "Received a test request at /log");
        return ResponseEntity.ok("Structured log created successfully.");
    }

    @GetMapping("/metrics/increment")
    public ResponseEntity<String> incrementCustomMetric() {
        customMetricCounter.increment();
        log.info("Custom metric incremented at /metrics/increment endpoint.");
        return ResponseEntity.ok("Custom metric incremented successfully.");
    }

    @GetMapping("/error/stack-overflow")
    public ResponseEntity<String> triggerStackOverflow() {
        try {
            triggerStackOverflowError();
        } catch (StackOverflowError e) {
            log.error("Simulated StackOverflowError triggered.", e);
            return ResponseEntity.status(500).body("StackOverflowError simulated successfully.");
        }
        return ResponseEntity.status(500).body("StackOverflowError was not triggered.");
    }

    @GetMapping("/error/out-of-memory")
    public ResponseEntity<String> triggerOutOfMemory() {
        try {
            triggerOutOfMemoryError();
        } catch (OutOfMemoryError e) {
            log.error("Simulated OutOfMemoryError triggered.", e);
            return ResponseEntity.status(500).body("OutOfMemoryError simulated successfully.");
        }
        return ResponseEntity.status(500).body("OutOfMemoryError was not triggered.");
    }

    private void logRequestWithUserId(String userId, String message) {
        if (userId != null) {
            MDC.put("userId", userId);
        }
        log.info(message);
        MDC.clear();
    }

    private void triggerStackOverflowError() {
        triggerStackOverflowError();
    }

    private void triggerOutOfMemoryError() {
        List<byte[]> memoryHog = new ArrayList<>();
        while (true) {
            memoryHog.add(new byte[1024 * 1024]);
        }
    }
}
