package com.helios.helios.observability.application.service.usecases.incident;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.IncidentRepository;

import java.util.List;

public class CreateIncident {

    private final IncidentRepository incidentRepository;

    public CreateIncident(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public Incident createIncident(MonitoredService service, Alert alert, IncidentSeverity severity){
        Incident incident = Incident.createNew(service, severity);
        return incidentRepository.save(incident);

    }
}
