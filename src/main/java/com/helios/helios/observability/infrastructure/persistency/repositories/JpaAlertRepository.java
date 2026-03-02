package com.helios.helios.observability.infrastructure.persistency.repositories;

import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAlertRepository extends JpaRepository<AlertEntity, Long> {

    List<AlertEntity> findByServiceIdAndSolvedAtIsNull(Long serviceId);
}
