package ru.lanit.interview.trygroovy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;
import ru.lanit.interview.trygroovy.entity.HealthHistoryEntity;
import ru.lanit.interview.trygroovy.mappper.HealthHistoryMapper;
import ru.lanit.interview.trygroovy.repository.HealthHistoryRepository;
import ru.lanit.interview.trygroovy.service.HealthHistoryService;

@Service
@RequiredArgsConstructor
public class HealHistoryServiceImpl implements HealthHistoryService {

    private final HealthHistoryRepository healthHistoryRepository;
    private final HealthHistoryMapper healthHistoryMapper;

    @Override
    public HealthHistoryDto saveHistory(HealthHistoryDto healthHistoryDto) {
        HealthHistoryEntity healthHistoryEntity = healthHistoryMapper.toEntity(healthHistoryDto);
        HealthHistoryEntity savedEntity = healthHistoryRepository.save(healthHistoryEntity);

        if (savedEntity != null) {
            return healthHistoryMapper.toDto(savedEntity);
        }

        return new  HealthHistoryDto();
    }

}
