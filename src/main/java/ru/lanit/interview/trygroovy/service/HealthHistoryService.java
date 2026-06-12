package ru.lanit.interview.trygroovy.service;

import java.util.List;
import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;

public interface HealthHistoryService {

    HealthHistoryDto saveHistory(HealthHistoryDto healthHistoryDto);

    List<HealthHistoryDto> getHistoryByPatientId(Long id);

    void deleteById(Long id);

}
