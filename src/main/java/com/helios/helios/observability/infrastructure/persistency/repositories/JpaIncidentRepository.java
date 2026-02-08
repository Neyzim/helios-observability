package com.helios.helios.observability.infrastructure.persistency.repositories;

import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaIncidentRepository extends JpaRepository<IncidentEntity, Long> {
}
