package com.helios.helios.observability.application.service.usecases.alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.AlertRepository;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;


public class CreateAlert {

    private final AlertRepository alertRepository;

    public CreateAlert(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;

    }

    public Alert createAlert(MonitoredService service,
                                        AlertType type){
        Alert alert = new Alert(service, type);
        alertRepository.save(alert);

        return alert;
    }
}
