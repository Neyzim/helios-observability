package com.helios.helios.observability.application.service.usecases.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.repository.IncidentRepository;

public class FinishIncidentBecauseServiceIsUp {

    private final IncidentRepository incidentRepository;

    public FinishIncidentBecauseServiceIsUp(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public void finish(Long serviceId){
        Incident incident = incidentRepository.findByServiceId(serviceId).orElseThrow();
        incident.finish();
        incidentRepository.save(incident);
    }
}
