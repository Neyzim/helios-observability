package com.helios.helios.observability.infrastructure.mapper.Alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AlertEntitiesMapper {

    private final MonitoredServiceEntitiesMapper mapper;

    public AlertEntitiesMapper(MonitoredServiceEntitiesMapper mapper) {
        this.mapper = mapper;
    }

    public Alert toCoreEntity(AlertEntity alertEntity){
        return Alert.rehydrate(
                alertEntity.getId(),
                mapper.toCoreEntity(alertEntity.getService()),
                alertEntity.getCreatedAt(),
                alertEntity.getSolvedAt(),
                alertEntity.getType()
        );
    }

    public AlertEntity toInfraEntity(Alert coreEntityAlert){
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setId(coreEntityAlert.id());
        alertEntity.setService(mapper.toInfraEntity(coreEntityAlert.Service()));
        alertEntity.setCreatedAt(coreEntityAlert.CreatedAt());
        alertEntity.setSolvedAt(coreEntityAlert.SolvedAt());
        alertEntity.setType(coreEntityAlert.Type());

        return alertEntity;
    }

    public List<Alert> listToCore(List<AlertEntity> infraAlerts){
        return infraAlerts.stream()
                .map(this::toCoreEntity)
                .collect(Collectors.toList());
    }

    public List<AlertEntity> listToInfra(List<Alert> coreAlerts){
        return coreAlerts.stream()
                .map(this::toInfraEntity)
                .collect(Collectors.toList());
    }
}
