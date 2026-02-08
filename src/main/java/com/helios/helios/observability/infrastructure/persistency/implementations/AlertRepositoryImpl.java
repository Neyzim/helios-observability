package com.helios.helios.observability.infrastructure.persistency.implementations;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.repository.AlertRepository;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaAlertRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final JpaAlertRepository alertRepository;
    private final AlertEntitiesMapper entitiesMapper;

    public AlertRepositoryImpl(JpaAlertRepository alertRepository, AlertEntitiesMapper entitiesMapper) {
        this.alertRepository = alertRepository;
        this.entitiesMapper = entitiesMapper;
    }

    @Override
    public Alert save(Alert alert) {
        AlertEntity alertEntity = entitiesMapper.toInfraEntity(alert);
        AlertEntity savedAlert = alertRepository.save(alertEntity);
        return entitiesMapper.toCoreEntity(savedAlert);
    }

    @Override
    public Optional<Alert> findAlertsById(Long id) {
        return alertRepository.findById(id).map(entitiesMapper::toCoreEntity);
    }

    @Override
    public List<Alert> findUnsolvedAlerts() {
        return alertRepository.findAll()
                .stream()
                .map(entitiesMapper::toCoreEntity)
                .toList();
    }

    @Override
    public List<Alert> findAlertsByIncidentId(Long id) {
        return alertRepository.findById(id)
                .stream()
                .map(entitiesMapper::toCoreEntity)
                .toList();
    }

    @Override
    public Optional<Alert> findByServiceId(Long id) {
        return alertRepository.findById(id)
                .map(entitiesMapper::toCoreEntity);
    }
}