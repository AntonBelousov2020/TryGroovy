package ru.lanit.interview.trygroovy.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "medical_history")
public class HealthHistoryEntity {

    @Id
    @SequenceGenerator(name = "health_history_changelog_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "health_history_changelog_seq")
    private Long id;

    @Column(name = "PATIENT_ID")
    private Long patientId;

    @Column(name = "DIAGNOSIS")
    private String diagnosis;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
