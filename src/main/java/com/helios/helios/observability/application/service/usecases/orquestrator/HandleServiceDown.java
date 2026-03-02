package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.AlertRepository;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.util.List;

public class HandleServiceDown {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final CreateIncident createIncident;
    private final CreateAlert createAlert;
    private final AlertRepository alertRepository;
    private final ObservabilityGateway observabilityGateway;

    public HandleServiceDown(MonitoredServiceRepository monitoredServiceRepository, CreateIncident createIncident, CreateAlert createAlert, AlertRepository alertRepository, ObservabilityGateway observabilityGateway) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.createIncident = createIncident;
        this.createAlert = createAlert;
        this.alertRepository = alertRepository;
        this.observabilityGateway = observabilityGateway;
    }

    public void serviceIsDown(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow(() -> new RuntimeException("Service Not Found!"));
        ServiceStateChange change = service.changeStatusToDown();


        if(change == ServiceStateChange.DOWN_CONFIRMED) {
            observabilityGateway.recordServiceDown(service.Name());
            Alert alert = createAlert.createAlert(service, AlertType.DOWN);
            IncidentSeverity severity = IncidentSeverity.from(service.Sla());
            Incident incident = createIncident.createIncident(service, alert, severity);
            List<Alert> alerts = alertRepository.findOpenAlertsByServiceId(service.Id());

            for(Alert a : alerts){
                a.assignIncident(incident.id());
                alertRepository.save(a);
            }

        }
        monitoredServiceRepository.save(service);
    }
}
