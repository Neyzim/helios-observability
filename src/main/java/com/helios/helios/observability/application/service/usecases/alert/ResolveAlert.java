package com.helios.helios.observability.application.service.usecases.alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.repository.AlertRepository;

import java.util.List;

public class ResolveAlert {

    private final AlertRepository alertRepository;

    public ResolveAlert(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public void resolve(Long serviceId){
        Alert alert = alertRepository.findAlertsById(serviceId).orElseThrow();
        alert.resolve();
        alertRepository.save(alert);
    }
}
