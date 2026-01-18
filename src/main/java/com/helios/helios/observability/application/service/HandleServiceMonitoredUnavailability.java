package com.helios.helios.observability.application.service;

import com.helios.helios.observability.application.dto.UnavailableMonitoredServiceSummaryDto;
import com.helios.helios.observability.application.mapper.ServiceMonitoredUnavailableMapper;
import com.helios.helios.observability.application.exception.MonitoredServiceNotFound;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class HandleServiceMonitoredUnavailability {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final ServiceMonitoredUnavailableMapper mapper;

    public HandleServiceMonitoredUnavailability(MonitoredServiceRepository monitoredServiceRepository, ServiceMonitoredUnavailableMapper mapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.mapper = mapper;
    }

    public UnavailableMonitoredServiceSummaryDto changeServiceStatusToDown(Long id){
        MonitoredService service = monitoredServiceRepository.findServiceById(id).orElseThrow(() -> new MonitoredServiceNotFound("Service Not found"));
        service.changeStatusToDown();
        monitoredServiceRepository.save(service);
        return mapper.toDto(service);
    }
}
