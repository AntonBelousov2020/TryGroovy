package ru.lanit.interview.trygroovy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;
import ru.lanit.interview.trygroovy.service.HealthHistoryService;

@RestController("/health/history")
public class HealthHistoryController {

    @Autowired
    private HealthHistoryService healthHistoryService;

    @PostMapping("/create")
    public ResponseEntity<HealthHistoryDto> saveHistory(@RequestBody HealthHistoryDto healthHistoryDto) {
        HealthHistoryDto healthHistorySavedDto = healthHistoryService.saveHistory(healthHistoryDto);
        return ResponseEntity.ok(healthHistorySavedDto);
    }

}
