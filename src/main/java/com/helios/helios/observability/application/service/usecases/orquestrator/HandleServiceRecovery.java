package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.core.domain.service.MonitoredService;

public class HandleServiceRecovery {


    private final FinishIncident finishIncident;
    private final ResolveAlert resolveAlert;

    public HandleServiceRecovery(FinishIncident finishIncident, ResolveAlert resolveAlert) {
        this.finishIncident = finishIncident;
        this.resolveAlert = resolveAlert;
    }

    public void resolve(MonitoredService service){

            resolveAlert.resolve(service.Id());
            finishIncident.finish(service.Id());

    }
}
