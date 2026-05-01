package ru.lanit.interview.trygroovy.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;
import ru.lanit.interview.trygroovy.service.HealthHistoryService;

@RestController
@RequestMapping("/health/history")
@RequiredArgsConstructor
public class HealthHistoryController {

    private final HealthHistoryService healthHistoryService;

    @PostMapping("/create")
    public ResponseEntity<HealthHistoryDto> saveHistory(@RequestBody HealthHistoryDto healthHistoryDto) {
        HealthHistoryDto healthHistorySavedDto = healthHistoryService.saveHistory(healthHistoryDto);
        return ResponseEntity.ok(healthHistorySavedDto);
    }

    @GetMapping("/get/{patientId}")
    public ResponseEntity<List<HealthHistoryDto>> getHistory(@PathVariable Long patientId) {
        List<HealthHistoryDto> healthHistoryDtos = healthHistoryService.getHistoryByPatientId(patientId);
        return healthHistoryDtos.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(healthHistoryDtos);
    }
}
