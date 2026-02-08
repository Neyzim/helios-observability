package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertEntitiesMapper;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import org.springframework.stereotype.Component;

@Component
public class IncidentEntitiesMapper {

    private final MonitoredServiceEntitiesMapper serviceMapper;
    private final AlertEntitiesMapper alertMapper;

    public IncidentEntitiesMapper(MonitoredServiceEntitiesMapper serviceMapper, AlertEntitiesMapper alertMapper) {
        this.serviceMapper = serviceMapper;
        this.alertMapper = alertMapper;
    }

    public Incident toCoreEntity(IncidentEntity incidentEntity){
        return Incident.createNew(
          serviceMapper.toCoreEntity(incidentEntity.getService()),
          alertMapper.listToCore(incidentEntity.getAlerts()),
          incidentEntity.getSeverity()
        );
    }

    public IncidentEntity toInfraEntity(Incident incident){
        IncidentEntity incidentEntity = new IncidentEntity();
        incidentEntity.setId(incident.id());
        incidentEntity.setAlerts(alertMapper.listToInfra(incident.alerts()));
        incidentEntity.setService(serviceMapper.toInfraEntity(incident.service()));
        incidentEntity.setFinishedAt(incident.finishedAt());
        incidentEntity.setStartedAt(incident.startedAt());
        incidentEntity.setSeverity(incident.severity());

        return incidentEntity;
    }
}
