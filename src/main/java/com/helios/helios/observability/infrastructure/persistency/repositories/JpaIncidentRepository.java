package com.helios.helios.observability.infrastructure.persistency.repositories;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaIncidentRepository extends JpaRepository<IncidentEntity, Long> {

    Optional<IncidentEntity> findByService_IdAndFinishedAtIsNull(Long serviceId);
}
