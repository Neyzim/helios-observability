package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.application.service.usecases.monitoredservice.ProcessServiceAvailabilityImp;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class HandleServiceRecovery {

    private final ProcessServiceAvailabilityImp processServiceAvailability;
    private final MonitoredServiceRepository monitoredServiceRepository;
    private final FinishIncident finishIncident;
    private final ResolveAlert resolveAlert;

    public HandleServiceRecovery(ProcessServiceAvailabilityImp processServiceAvailability, MonitoredServiceRepository monitoredServiceRepository, FinishIncident finishIncident, ResolveAlert resolveAlert) {
        this.processServiceAvailability = processServiceAvailability;
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.finishIncident = finishIncident;
        this.resolveAlert = resolveAlert;
    }

    public void resolve(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow();
        processServiceAvailability.serviceIsUp(service);
        resolveAlert.resolve(serviceId);
        finishIncident.finish(serviceId);
    }
}
