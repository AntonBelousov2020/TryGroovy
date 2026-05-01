package ru.lanit.interview.trygroovy.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lanit.interview.trygroovy.entity.HealthHistoryEntity;

@Repository
public interface HealthHistoryRepository extends JpaRepository<HealthHistoryEntity, Long> {

    List<HealthHistoryEntity> getHistoryByPatientId(Long id);

}
