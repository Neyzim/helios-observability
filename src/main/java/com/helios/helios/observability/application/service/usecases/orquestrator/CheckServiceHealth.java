package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.gateway.HealthCheckGateway;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.util.List;

public class CheckServiceHealth {

    private final MonitoredServiceRepository monitoredServiceRepository;
    private final HealthCheckGateway healthCheckGateway;
    private final ObservabilityGateway observabilityGateway;

    public CheckServiceHealth(MonitoredServiceRepository monitoredServiceRepository, HealthCheckGateway healthCheckGateway, ObservabilityGateway observabilityGateway) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.healthCheckGateway = healthCheckGateway;
        this.observabilityGateway = observabilityGateway;
    }

    public void execute(){
       List<MonitoredService> monitoredServices = monitoredServiceRepository.listAllServices();

       for(MonitoredService service : monitoredServices){
           boolean isUp = healthCheckGateway.isServiceUp(service.MonitoredEndpoint());
           if(isUp){
               service.changeStatusToUp();
               monitoredServiceRepository.save(service);
               observabilityGateway.recordServiceUp(service.Name());
           }else {
               service.changeStatusToDown();
               monitoredServiceRepository.save(service);
               observabilityGateway.recordServiceDown(service.Name());
           }
           }
    }
}
