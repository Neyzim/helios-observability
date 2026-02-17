package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;

public class ServiceHealthHandler {

    private final HandleServiceDown handleServiceDown;
    private final HandleServiceRecovery handleServiceRecovery;

    public ServiceHealthHandler(HandleServiceDown handleServiceDown, HandleServiceRecovery handleServiceRecovery) {
        this.handleServiceDown = handleServiceDown;
        this.handleServiceRecovery = handleServiceRecovery;
    }

    public void handle(MonitoredService service, boolean isUp){
        ServiceStateChange change;
        if(isUp){
            change = service.changeStatusToUp();

            if(change == ServiceStateChange.UP_CONFIRMED) {
                handleServiceRecovery.resolve(service.Id());
            }
        }else{
            change = service.changeStatusToDown();

            if(change == ServiceStateChange.DOWN_CONFIRMED) {
                handleServiceDown.serviceIsDown(service.Id());
            }
        }

    }
}
