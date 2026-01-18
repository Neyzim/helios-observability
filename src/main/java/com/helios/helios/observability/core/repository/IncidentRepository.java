package com.helios.helios.observability.core.repository;

import com.helios.helios.observability.core.domain.incident.Incident;

import java.util.List;
import java.util.Optional;

public interface IncidentRepository {

    Incident save(Incident incident);

    Optional<Incident> findByServiceId(Long id);

    Optional<Incident> findById(Long id);

    List<Incident> findOpenIncidents();

    void close(Incident incident);


}
