package com.helios.helios.observability.infrastructure.mapper.Alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceEntitiesMapper;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceMapperUtil;
import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import com.helios.helios.observability.infrastructure.persistency.entities.IncidentEntity;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertEntitiesMapper {

    private final EntityManager entityManager;

    public AlertEntitiesMapper(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Alert toCoreEntity(AlertEntity alertEntity){
        if(alertEntity ==null){
            return null;
        }
        Long incidentId = null;
        if (alertEntity.getIncident() != null ){
            incidentId =   alertEntity.getIncident().getId();
        }
        return Alert.rehydrate(
                alertEntity.getId(),
                MonitoredServiceMapperUtil.toCoreEntity(alertEntity.getService()),
                alertEntity.getCreatedAt(),
                alertEntity.getSolvedAt(),
                alertEntity.getType(),
                incidentId
        );
    }

    public AlertEntity toInfraEntity(Alert coreEntityAlert){
        if(coreEntityAlert ==null){
            return null;
        }
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setId(coreEntityAlert.id());
        alertEntity.setService(MonitoredServiceMapperUtil.toInfraEntity(coreEntityAlert.Service()));
        alertEntity.setCreatedAt(coreEntityAlert.CreatedAt());
        alertEntity.setSolvedAt(coreEntityAlert.SolvedAt());
        alertEntity.setType(coreEntityAlert.Type());
        if(coreEntityAlert.IncidentId() != null){
            IncidentEntity incidentRef = entityManager.getReference(IncidentEntity.class,
                                                                coreEntityAlert.IncidentId());
            alertEntity.setIncident(incidentRef);
        }

        return alertEntity;
    }

    public List<Alert> listToCore(List<AlertEntity> infraAlerts){
        if(infraAlerts ==null){
            return null;
        }
        return infraAlerts.stream()
                .map(this::toCoreEntity)
                .collect(Collectors.toList());
    }

    public List<AlertEntity> listToInfra(List<Alert> coreAlerts){
        if(coreAlerts ==null){
            return null;
        }
        return coreAlerts.stream()
                .map(this::toInfraEntity)
                .collect(Collectors.toList());
    }
}
