package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;

import java.util.List;

public class StartIncidentRaisedByAlertServiceDown {

    private final CreateIncident createIncident;

    public StartIncidentRaisedByAlertServiceDown(CreateIncident createIncident) {
        this.createIncident = createIncident;
    }

    public void start(MonitoredService service, Alert alert){
        createIncident.createIncident(service, alert, IncidentSeverity.DISASTER);
    }
}
