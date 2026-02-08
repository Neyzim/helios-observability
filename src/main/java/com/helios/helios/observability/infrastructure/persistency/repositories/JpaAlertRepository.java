package com.helios.helios.observability.infrastructure.persistency.repositories;

import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAlertRepository extends JpaRepository<AlertEntity, Long> {
}
