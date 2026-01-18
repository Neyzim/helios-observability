package com.helios.helios.observability.application.service;

import com.helios.helios.observability.application.dto.MonitoredServiceResponseDto;
import com.helios.helios.observability.application.mapper.MonitoredServiceMapper;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class RegisterMonitoredService {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final MonitoredServiceMapper mapper;

    public RegisterMonitoredService(MonitoredServiceRepository monitoredServiceRepository, MonitoredServiceMapper mapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.mapper = mapper;
    }

    public MonitoredServiceResponseDto registerNewMonitoredService(
                                                                String name,
                                                                String monitoredEndpoint,
                                                                SLAServiceEnum sla
    )
    {
        MonitoredService service = MonitoredService.createNew(
                name, monitoredEndpoint, sla
        );
        monitoredServiceRepository.save(service);
        return mapper.toDto(service);
    }
}
