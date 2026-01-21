package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncidentBecauseServiceIsUp;
import com.helios.helios.observability.application.service.usecases.monitoredservice.ProcessServiceAvailability;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class HandleServiceRecovery {

    private final ProcessServiceAvailability processServiceAvailability;
    private final MonitoredServiceRepository monitoredServiceRepository;
    private final FinishIncidentBecauseServiceIsUp finishIncident;
    private final ResolveAlert resolveAlert;

    public HandleServiceRecovery(ProcessServiceAvailability processServiceAvailability, MonitoredServiceRepository monitoredServiceRepository, FinishIncidentBecauseServiceIsUp finishIncident, ResolveAlert resolveAlert) {
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
