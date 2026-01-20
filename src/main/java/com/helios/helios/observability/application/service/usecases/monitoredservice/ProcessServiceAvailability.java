package com.helios.helios.observability.application.service.usecases.monitoredservice;

import com.helios.helios.observability.application.exception.MonitoredServiceNotFound;
import com.helios.helios.observability.application.service.input.ServiceAvailabilityInput;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

public class ProcessServiceAvailability implements ServiceAvailabilityInput {

    private final MonitoredServiceRepository monitoredServiceRepository;

    public ProcessServiceAvailability(MonitoredServiceRepository monitoredServiceRepository) {
        this.monitoredServiceRepository = monitoredServiceRepository;

    }

    @Override
    public void serviceIsDown(MonitoredService service){
        service.changeStatusToDown();
        monitoredServiceRepository.save(service);
    }

    @Override
    public void serviceIsUp(MonitoredService service){
        service.changeStatusToUp();
        monitoredServiceRepository.save(service);
    }
}
