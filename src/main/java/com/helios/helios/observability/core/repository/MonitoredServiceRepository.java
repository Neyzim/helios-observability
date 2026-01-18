package com.helios.helios.observability.core.repository;

import com.helios.helios.observability.core.domain.service.MonitoredService;

public interface MonitoredServiceRepository {

    MonitoredService save(MonitoredService service);
}
