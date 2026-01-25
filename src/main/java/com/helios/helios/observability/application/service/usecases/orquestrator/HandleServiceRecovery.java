package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class HandleServiceRecovery {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final FinishIncident finishIncident;
    private final ResolveAlert resolveAlert;

    public HandleServiceRecovery(MonitoredServiceRepository monitoredServiceRepository, FinishIncident finishIncident, ResolveAlert resolveAlert) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.finishIncident = finishIncident;
        this.resolveAlert = resolveAlert;
    }

    public void resolve(Long serviceId){
        MonitoredService service = monitoredServiceRepository.findServiceById(serviceId).orElseThrow();
        ServiceStateChange change = service.changeStatusToUp();
        if (change == ServiceStateChange.UP_CONFIRMED) {
            resolveAlert.resolve(serviceId);
            finishIncident.finish(serviceId);
        }
        monitoredServiceRepository.save(service);
    }
}
