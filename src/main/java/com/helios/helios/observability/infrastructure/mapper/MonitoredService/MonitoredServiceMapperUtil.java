package com.helios.helios.observability.infrastructure.mapper.MonitoredService;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class MonitoredServiceMapperUtil {

    public MonitoredServiceMapperUtil() {
    }

    public static MonitoredService toCoreEntity(MonitoredServiceEntity infraEntity){
        MonitoredService service = MonitoredService.rehydrate(
                infraEntity.getId(),
                infraEntity.getServiceName(),
                infraEntity.getMonitoredEndpoint(),
                infraEntity.getStatus(),
                infraEntity.getSla(),
                infraEntity.getCont(),
                infraEntity.getLastEvent(),
                null
        );
        IncidentEntity incidentEntity = infraEntity.getIncident();
        if(incidentEntity != null){
            Incident incident = Incident.rehydrate(
                    incidentEntity.getStartedAt(),
                    incidentEntity.getId(),
                    service,
                    incidentEntity.getFinishedAt(),
                    incidentEntity.getSeverity()

            );
            service.attachIncident(incident);
        }
        return service;
    }

    public static MonitoredServiceEntity toInfraEntity(MonitoredService coreService){
        MonitoredServiceEntity monitoredServiceEntity = new MonitoredServiceEntity();
        monitoredServiceEntity.setId(coreService.Id());
        monitoredServiceEntity.setMonitoredEndpoint(coreService.MonitoredEndpoint());
        monitoredServiceEntity.setServiceName(coreService.Name());
        monitoredServiceEntity.setStatus(coreService.Status());
        monitoredServiceEntity.setSla(coreService.Sla());
        monitoredServiceEntity.setCont(coreService.Cont());
        monitoredServiceEntity.setLastEvent(coreService.LastEvent());

        if (coreService.Incident() != null) {
            IncidentEntity incidentEntity = new IncidentEntity();
            incidentEntity.setId(coreService.Incident().id());
            monitoredServiceEntity.setIncident(incidentEntity);
        }

        return monitoredServiceEntity;
    }
}
