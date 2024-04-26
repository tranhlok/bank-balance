package dev.codescreen.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
public class PingController {
    @GetMapping("/ping")
    public ResponseEntity<Object> ping() {
        // Fetch the current server time
        LocalDateTime now = LocalDateTime.now();
        // Format it to ISO_LOCAL_DATE_TIME format (e.g., "2007-12-03T10:15:30")
        String formattedTime = now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);

        // Return a JSON object with the server time
        return ResponseEntity.ok(new Object() {
            public final String serverTime = formattedTime;
        });
    }
}
