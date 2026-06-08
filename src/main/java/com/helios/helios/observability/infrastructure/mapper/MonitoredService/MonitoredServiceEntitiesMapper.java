package com.helios.helios.observability.infrastructure.mapper.MonitoredService;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertEntitiesMapper;
import com.helios.helios.observability.infrastructure.mapper.incident.IncidentEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class MonitoredServiceEntitiesMapper {

    private final AlertEntitiesMapper alertEntitiesMapper;
    private final IncidentEntitiesMapper incidentEntitiesMapper;

    public MonitoredServiceEntitiesMapper(AlertEntitiesMapper alertEntitiesMapper, IncidentEntitiesMapper incidentEntitiesMapper) {
        this.alertEntitiesMapper = alertEntitiesMapper;
        this.incidentEntitiesMapper = incidentEntitiesMapper;
    }

    public MonitoredService toCoreEntity(MonitoredServiceEntity infraEntity){
        return MonitoredService.rehydrate(
                infraEntity.getId(),
                infraEntity.getServiceName(),
                infraEntity.getMonitoredEndpoint(),
                infraEntity.getStatus(),
                infraEntity.getSla(),
                infraEntity.getCont(),
                infraEntity.getLastEvent(),
                incidentEntitiesMapper.toCoreEntity(infraEntity.getIncident())
        );
    }

    public MonitoredServiceEntity toInfraEntity(MonitoredService coreService){
        MonitoredServiceEntity monitoredServiceEntity = new MonitoredServiceEntity();
        monitoredServiceEntity.setId(coreService.Id());
        monitoredServiceEntity.setMonitoredEndpoint(coreService.MonitoredEndpoint());
        monitoredServiceEntity.setServiceName(coreService.Name());
        monitoredServiceEntity.setStatus(coreService.Status());
        monitoredServiceEntity.setSla(coreService.Sla());
        monitoredServiceEntity.setCont(coreService.Cont());
        monitoredServiceEntity.setLastEvent(coreService.LastEvent());
        monitoredServiceEntity.setAlerts(alertEntitiesMapper.listToInfra(coreService.Alerts()));
        monitoredServiceEntity.setIncident(incidentEntitiesMapper.toInfraEntity(coreService.Incident()));

        return monitoredServiceEntity;
    }
}
