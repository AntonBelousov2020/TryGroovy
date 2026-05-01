package ru.lanit.interview.trygroovy.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HealthHistoryDto {

    private Long patientId;

    private String diagnosis;

    private String description;

    private LocalDateTime createdAt;

}
