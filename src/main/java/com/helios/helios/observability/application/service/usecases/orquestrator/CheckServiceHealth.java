package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.gateway.HealthCheckGateway;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.util.List;

public class CheckServiceHealth {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final HealthCheckGateway healthCheckGateway;
    private final ServiceHealthHandler serviceHealthHandler;
    private final ObservabilityGateway observabilityGateway;


    public CheckServiceHealth(MonitoredServiceRepository monitoredServiceRepository, HealthCheckGateway healthCheckGateway, ServiceHealthHandler serviceHealthHandler, ObservabilityGateway observabilityGateway) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.healthCheckGateway = healthCheckGateway;

        this.serviceHealthHandler = serviceHealthHandler;
        this.observabilityGateway = observabilityGateway;
    }

    public void execute() {
        List<MonitoredService> monitoredServices = monitoredServiceRepository.listAllServices();

        for (MonitoredService service : monitoredServices) {
            boolean isUp = healthCheckGateway.isServiceUp(service.MonitoredEndpoint());
            serviceHealthHandler.handle(service, isUp);

            monitoredServiceRepository.save(service);

            if (isUp) {
                observabilityGateway.recordServiceUp(service.Name());
            } else {
                observabilityGateway.recordServiceDown(service.Name());
            }
        }
    }
}