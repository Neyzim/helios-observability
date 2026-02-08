package com.helios.helios.observability.infrastructure.mapper.Alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.persistency.entities.AlertEntity;

public class AlertEntitiesMapper {

    public Alert toCoreEntity(AlertEntity alertEntity){
        Alert alert = Alert.rehydrate(
                alertEntity.getId(),
                alertEntity.getService(),
                alertEntity.getCreatedAt(),
                alertEntity.getSolvedAt(),
                alertEntity.getType()
        );
        return alert;
    }

    public AlertEntity toInfraEntity(Alert coreEntityAlert){
        AlertEntity alertEntity = new AlertEntity();
        alertEntity.setId(coreEntityAlert.id());
        alertEntity.setService(coreEntityAlert.Service());
        alertEntity.setCreatedAt(coreEntityAlert.CreatedAt());
        alertEntity.setSolvedAt(coreEntityAlert.SolvedAt());
        alertEntity.setType(coreEntityAlert.Type());

        return alertEntity;
    }

}
