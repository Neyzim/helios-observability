package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.util.List;

public class HandleServiceDown {

    private final RaiseServiceDownAlert raiseServiceDownAlert;
    private final MonitoredServiceRepository monitoredServiceRepository;
    private final StartIncidentRaisedByAlertServiceDown startIncident;

    public HandleServiceDown(RaiseServiceDownAlert raiseServiceDownAlert, MonitoredServiceRepository monitoredServiceRepository, StartIncidentRaisedByAlertServiceDown startIncident) {
        this.raiseServiceDownAlert = raiseServiceDownAlert;
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.startIncident = startIncident;
    }

    public void serviceIsDown(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow();
        Alert alert = raiseServiceDownAlert.raise(service);
        startIncident.start(service, alert);
    }
}
