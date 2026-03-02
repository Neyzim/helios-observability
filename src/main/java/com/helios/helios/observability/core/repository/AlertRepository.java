package com.helios.helios.observability.core.repository;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.service.MonitoredService;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {

    Alert save (Alert alert);

    Optional<Alert> findAlertById(Long id);

    List<Alert> findUnsolvedAlerts();

    List<Alert> findAlertsByIncidentId(Long id);

    List<Alert> findAlertsByServiceId(Long id);

    List<Alert> findOpenAlertsByServiceId(Long serviceId);
}
