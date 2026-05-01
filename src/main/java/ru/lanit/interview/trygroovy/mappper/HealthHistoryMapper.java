package ru.lanit.interview.trygroovy.mappper;

import org.mapstruct.Mapper;
import ru.lanit.interview.trygroovy.dto.HealthHistoryDto;
import ru.lanit.interview.trygroovy.entity.HealthHistoryEntity;

@Mapper(componentModel = "spring")
public interface HealthHistoryMapper {

    HealthHistoryDto toDto(HealthHistoryEntity entity);

    HealthHistoryEntity toEntity(HealthHistoryDto dto);

}
