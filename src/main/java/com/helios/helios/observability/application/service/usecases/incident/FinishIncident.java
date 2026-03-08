package com.helios.helios.observability.application.service.usecases.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.repository.IncidentRepository;

import java.util.List;

public class FinishIncident {

    private final IncidentRepository incidentRepository;

    public FinishIncident(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public void finish(Long serviceId){
       List<Incident> incidents = incidentRepository.findOpenIncidentByServiceId(serviceId);

       for(Incident i : incidents){
           i.finish();
           incidentRepository.save(i);
       }

    }
}
