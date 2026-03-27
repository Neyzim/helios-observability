package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.AlertRepository;

import java.util.List;

public class HandleServiceDown {


    private final CreateIncident createIncident;
    private final CreateAlert createAlert;
    private final AlertRepository alertRepository;

    public HandleServiceDown(CreateIncident createIncident, CreateAlert createAlert, AlertRepository alertRepository) {
        this.createIncident = createIncident;
        this.createAlert = createAlert;
        this.alertRepository = alertRepository;
    }

    public void serviceIsDown(MonitoredService service){

            Alert alert = createAlert.createAlert(service, AlertType.DOWN);
            IncidentSeverity severity = IncidentSeverity.from(service.Sla());
            Incident incident = createIncident.createIncident(service, alert, severity);
            List<Alert> alerts = alertRepository.findOpenAlertsByServiceId(service.Id());

            for(Alert a : alerts){
                a.assignIncident(incident.id());
            }

    }
}
