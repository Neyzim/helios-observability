package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class HandleServiceDown {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final CreateIncident createIncident;
    private final CreateAlert createAlert;

    public HandleServiceDown(MonitoredServiceRepository monitoredServiceRepository, CreateIncident createIncident, CreateAlert createAlert) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.createIncident = createIncident;
        this.createAlert = createAlert;
    }

    public void serviceIsDown(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow(() -> new RuntimeException("Service Not Found!"));
        ServiceStateChange change = service.changeStatusToDown();
        if(change == ServiceStateChange.DOWN_CONFIRMED) {
            Alert alert = createAlert.createAlert(service, AlertType.DOWN);
            IncidentSeverity severity = IncidentSeverity.from(service.Sla());
            createIncident.createIncident(service, alert, severity);
        }
        monitoredServiceRepository.save(service);
    }
}
