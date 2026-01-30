package com.helios.helios.observability.infrastructure.persistency.repositories;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public  interface JpaMonitoredServiceRepository extends JpaRepository<MonitoredService, Long> {

    MonitoredService findAllServicesByStatus(StatusEnum status);

    MonitoredService findByServiceName(String name);
}
