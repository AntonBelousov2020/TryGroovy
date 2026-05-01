package ru.lanit.interview.trygroovy.service;

import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;

public interface HealthHistoryService {

    HealthHistoryDto saveHistory(HealthHistoryDto healthHistoryDto);

}
