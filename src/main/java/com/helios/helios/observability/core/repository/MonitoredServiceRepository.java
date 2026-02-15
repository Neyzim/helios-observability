package com.helios.helios.observability.core.repository;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.StatusEnum;

import java.util.List;
import java.util.Optional;

public interface MonitoredServiceRepository {

    MonitoredService save(MonitoredService service);

    Optional<MonitoredService> findServiceById(Long Id);

    List<MonitoredService> listMonitoredServicesForStatus(StatusEnum status);

    Optional<MonitoredService> findServiceByName(String name);

    List<MonitoredService> listAllServices();
}
