package com.helios.helios.observability.infrastructure.mapper;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;

public class EntitiesMapper {

    public MonitoredService toCoreEntity(MonitoredServiceEntity infraEntity){
        return MonitoredService.createNew(
                infraEntity.getName(),
                infraEntity.getMonitoredEndpoint(),
                infraEntity.getSla()
        );
    }

    public MonitoredServiceEntity toInfraEntity(MonitoredService coreService){
        MonitoredServiceEntity monitoredServiceEntity = new MonitoredServiceEntity();
        monitoredServiceEntity.setId(coreService.Id());
        monitoredServiceEntity.setMonitoredEndpoint(coreService.MonitoredEndpoint());
        monitoredServiceEntity.setName(coreService.Name());
        monitoredServiceEntity.setStatus(coreService.Status());
        monitoredServiceEntity.setSla(coreService.Sla());

        return monitoredServiceEntity;
    }
}
