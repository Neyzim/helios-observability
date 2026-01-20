package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.monitoredservice.ProcessServiceAvailability;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;

public class RaiseServiceDownAlert {

    private final ProcessServiceAvailability availability;
    private final CreateAlert createAlert;

    public RaiseServiceDownAlert(ProcessServiceAvailability availability, CreateAlert createAlert) {
        this.availability = availability;
        this.createAlert = createAlert;
    }

    public Alert raise(MonitoredService service){
        availability.serviceIsDown(service);
        return createAlert.createAlert(service, AlertType.DOWN);
    }
}
