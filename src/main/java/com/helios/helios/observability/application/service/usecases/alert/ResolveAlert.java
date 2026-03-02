package com.helios.helios.observability.application.service.usecases.alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.AlertRepository;

import java.util.List;

public class ResolveAlert {

    private final AlertRepository alertRepository;

    public ResolveAlert(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void resolve(Long serviceId){
       List<Alert> alerts = alertRepository.findOpenAlertsByServiceId(serviceId);

       for(Alert a : alerts){
           a.resolve();
           alertRepository.save(a);
       }
    }
}
