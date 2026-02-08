package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;

public class IncidentEntitiesMapper {


    public Incident toCoreEntity(IncidentEntity incidentEntity){
        return Incident.createNew(
          incidentEntity.getService(),
          incidentEntity.getAlerts(),
          incidentEntity.getSeverity()
        );
    }

    public IncidentEntity toInfraEntity(Incident incident){
        IncidentEntity incidentEntity = new IncidentEntity();
        incidentEntity.setId(incident.id());
        incidentEntity.setAlerts(incident.alerts());
        incidentEntity.setService(incident.service());
        incidentEntity.setFinishedAt(incident.finishedAt());
        incidentEntity.setStartedAt(incident.startedAt());
        incidentEntity.setSeverity(incident.severity());

        return incidentEntity;
    }
}
