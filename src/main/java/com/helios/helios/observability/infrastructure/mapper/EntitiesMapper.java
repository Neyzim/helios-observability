package com.helios.helios.observability.infrastructure.mapper;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class EntitiesMapper {

    public MonitoredService toCoreEntity(MonitoredServiceEntity infraEntity){
        return MonitoredService.rehydrate(
                infraEntity.getId(),
                infraEntity.getServiceName(),
                infraEntity.getMonitoredEndpoint(),
                infraEntity.getStatus(),
                infraEntity.getSla()
        );
    }

    public MonitoredServiceEntity toInfraEntity(MonitoredService coreService){
        MonitoredServiceEntity monitoredServiceEntity = new MonitoredServiceEntity();
        monitoredServiceEntity.setId(coreService.Id());
        monitoredServiceEntity.setMonitoredEndpoint(coreService.MonitoredEndpoint());
        monitoredServiceEntity.setServiceName(coreService.Name());
        monitoredServiceEntity.setStatus(coreService.Status());
        monitoredServiceEntity.setSla(coreService.Sla());

        return monitoredServiceEntity;
    }
}
