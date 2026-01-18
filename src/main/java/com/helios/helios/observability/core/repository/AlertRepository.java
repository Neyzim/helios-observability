package com.helios.helios.observability.core.repository;

import com.helios.helios.observability.core.domain.alert.Alert;

import java.util.List;
import java.util.Optional;

public interface AlertRepository {

    Alert save (Alert alert);

    Optional<Alert> findAlertsById(Long id);

    List<Alert> findUnsolvedAlerts();

    List<Alert> findAlertsByIncidentId(Long id);
}
