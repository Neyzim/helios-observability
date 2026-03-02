package com.helios.helios.observability.application.service.usecases.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.repository.IncidentRepository;

public class FinishIncident {

    private final IncidentRepository incidentRepository;

    public FinishIncident(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public void finish(Long incidentId){
       incidentRepository.findOpenIncidentByServiceId(incidentId)
               .ifPresent(incident -> {
                   incident.finish();
                   incidentRepository.save(incident);
               });

    }
}
