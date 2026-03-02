package com.helios.helios.observability.infrastructure.persistency.implementations;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.repository.IncidentRepository;
import com.helios.helios.observability.infrastructure.mapper.incident.IncidentEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaIncidentRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class IncidentRepositoryImpl implements IncidentRepository {

    private final JpaIncidentRepository incidentRepository;
    private final IncidentEntitiesMapper mapper;

    public IncidentRepositoryImpl(JpaIncidentRepository incidentRepository, IncidentEntitiesMapper mapper) {
        this.incidentRepository = incidentRepository;
        this.mapper = mapper;
    }

    @Override
    public Incident save(Incident incident) {
        IncidentEntity entity = mapper.toInfraEntity(incident);
        IncidentEntity savedIncident = incidentRepository.save(entity);
        return mapper.toCoreEntity(savedIncident);
    }

    @Override
    public Optional<Incident> findByServiceId(Long id) {
        return incidentRepository.findById(id)
                .map(mapper::toCoreEntity);
    }

    @Override
    public Optional<Incident> findById(Long id) {
        return incidentRepository.findById(id)
                .map(mapper::toCoreEntity);
    }

    @Override
    public Optional<Incident> findOpenIncidentByServiceId(Long serviceId) {
        return incidentRepository.findByService_IdAndFinishedAtIsNull(serviceId)
                .map(mapper::toCoreEntity);
    }
}
