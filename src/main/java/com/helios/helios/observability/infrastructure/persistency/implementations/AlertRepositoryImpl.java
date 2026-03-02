package com.helios.helios.observability.infrastructure.persistency.implementations;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.AlertRepository;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaAlertRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final JpaAlertRepository alertRepository;
    private final AlertEntitiesMapper entitiesMapper;
    private final IncidentRepositoryImpl incidentRepository;
    private final MonitoredServiceRepositoryImpl monitoredServiceRepository;

    public AlertRepositoryImpl(JpaAlertRepository alertRepository, AlertEntitiesMapper entitiesMapper, IncidentRepositoryImpl incidentRepository, MonitoredServiceRepositoryImpl monitoredServiceRepository) {
        this.alertRepository = alertRepository;
        this.entitiesMapper = entitiesMapper;
        this.incidentRepository = incidentRepository;
        this.monitoredServiceRepository = monitoredServiceRepository;
    }

    @Override
    public Alert save(Alert alert) {
        AlertEntity alertEntity = entitiesMapper.toInfraEntity(alert);
        AlertEntity savedAlert = alertRepository.save(alertEntity);
        return entitiesMapper.toCoreEntity(savedAlert);
    }

    @Override
    public Optional<Alert> findAlertById(Long id) {
        return alertRepository.findById(id).map(entitiesMapper::toCoreEntity);
    }

    @Override
    public List<Alert> findUnsolvedAlerts() {
        List<Alert> alerts = alertRepository.findAll()
                            .stream()
                            .map(entitiesMapper::toCoreEntity)
                            .toList();
        List<Alert> unsolvedAlerts = new ArrayList<>();
        for (Alert alert : alerts){
            if (alert.SolvedAt() == null){
                unsolvedAlerts.add(alert);
            }
        }
        return unsolvedAlerts;
    }

    @Override
    public List<Alert> findAlertsByIncidentId(Long id) {
        Incident incident = incidentRepository.findById(id).orElseThrow();
        List<Alert> alerts = incident.service().Alerts();
        return alerts;
    }

    @Override
    public List<Alert> findAlertsByServiceId(Long id) {
        MonitoredService service = monitoredServiceRepository.findServiceById(id)
                .orElseThrow();
        List<Alert> alerts = service.Alerts();

        return alerts;
    }

    @Override
    public List<Alert> findOpenAlertsByServiceId(Long serviceId) {
        List<AlertEntity> alerts = alertRepository.findByServiceIdAndSolvedAtIsNull(serviceId);
        return entitiesMapper.listToCore(alerts);
    }
}