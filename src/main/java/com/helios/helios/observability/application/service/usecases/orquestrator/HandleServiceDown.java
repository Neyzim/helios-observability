package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.application.service.usecases.monitoredservice.ProcessServiceAvailabilityImp;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.util.List;

public class HandleServiceDown {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final CreateIncident createIncident;
    private final ProcessServiceAvailabilityImp availability;
    private final CreateAlert createAlert;

    public HandleServiceDown(MonitoredServiceRepository monitoredServiceRepository, CreateIncident createIncident, ProcessServiceAvailabilityImp availability, CreateAlert createAlert) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.createIncident = createIncident;
        this.availability = availability;
        this.createAlert = createAlert;
    }

    public void serviceIsDown(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow(() -> new RuntimeException("Service Not Found!"));
        availability.serviceIsDown(service);
        Alert alert = createAlert.createAlert(service, AlertType.DOWN);
        IncidentSeverity severity = IncidentSeverity.from(service.Sla());
        createIncident.createIncident(service, alert, severity);
    }
}
