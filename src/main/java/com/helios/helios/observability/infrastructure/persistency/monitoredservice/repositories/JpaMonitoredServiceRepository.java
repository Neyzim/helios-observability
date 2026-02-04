package com.helios.helios.observability.infrastructure.persistency.monitoredservice.repositories;


import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.infrastructure.persistency.monitoredservice.entities.MonitoredServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public  interface JpaMonitoredServiceRepository extends JpaRepository<MonitoredServiceEntity, Long> {

    List<MonitoredServiceEntity> findAllServicesByStatus(StatusEnum status);

    MonitoredServiceEntity findByServiceName(String name);
}
