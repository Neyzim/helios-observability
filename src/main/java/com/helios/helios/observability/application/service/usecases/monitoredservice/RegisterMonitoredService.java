package com.helios.helios.observability.application.service.usecases.monitoredservice;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class RegisterMonitoredService {

    private final MonitoredServiceRepository monitoredServiceRepository;

    public RegisterMonitoredService(MonitoredServiceRepository monitoredServiceRepository) {
        this.monitoredServiceRepository = monitoredServiceRepository;
    }

    public MonitoredService registerNewMonitoredService(
                                                                String name,
                                                                String monitoredEndpoint,
                                                                SLAServiceEnum sla
    )
    {
        MonitoredService service = MonitoredService.createNew(
                name, monitoredEndpoint, sla
        );
        monitoredServiceRepository.save(service);
        return service;
    }
}
