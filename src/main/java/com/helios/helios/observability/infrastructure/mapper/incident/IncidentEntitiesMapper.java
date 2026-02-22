package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertEntitiesMapper;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceEntitiesMapper;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceMapperUtil;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class IncidentEntitiesMapper {

    private final AlertEntitiesMapper alertMapper;

    public IncidentEntitiesMapper(AlertEntitiesMapper alertMapper) {

        this.alertMapper = alertMapper;
    }

    public Incident toCoreEntity(IncidentEntity incidentEntity){
        if(incidentEntity == null){
            return null;
        }
        return Incident.createNew(
        incidentEntity.getService() != null
          ? MonitoredServiceMapperUtil.toCoreEntity(incidentEntity.getService())
          : null,
        incidentEntity.getAlerts() != null
            ? alertMapper.listToCore(incidentEntity.getAlerts())
            : null,
            incidentEntity.getSeverity()
        );
    }

    public IncidentEntity toInfraEntity(Incident incident){
        if(incident == null){
            return null;
        }
        IncidentEntity incidentEntity = new IncidentEntity();
        incidentEntity.setId(incident.id());
        incidentEntity.setAlerts(incident.alerts() != null
            ? alertMapper.listToInfra(incident.alerts())
            : List.of());

        incidentEntity.setService( incident.service() != null
                ? MonitoredServiceMapperUtil.toInfraEntity(incident.service())
                : null);
        incidentEntity.setFinishedAt(incident.finishedAt());
        incidentEntity.setStartedAt(incident.startedAt());
        incidentEntity.setSeverity(incident.severity());

        return incidentEntity;
    }
}
