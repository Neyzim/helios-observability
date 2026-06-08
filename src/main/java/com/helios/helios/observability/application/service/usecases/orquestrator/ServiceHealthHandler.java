package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOfflineEvent;
import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOnlineEvent;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;

import java.time.LocalDateTime;

public class ServiceHealthHandler {

    private final HandleServiceDown handleServiceDown;
    private final HandleServiceRecovery handleServiceRecovery;
    private final MonitoredServiceRepository monitoredServiceRepository;
    private final ObservabilityGateway observabilityGateway;
    private final ServiceEventPublisher serviceEventPublisher;

    public ServiceHealthHandler(HandleServiceDown handleServiceDown, HandleServiceRecovery handleServiceRecovery, MonitoredServiceRepository monitoredServiceRepository, ObservabilityGateway observabilityGateway, ServiceEventPublisher serviceEventPublisher) {
        this.handleServiceDown = handleServiceDown;
        this.handleServiceRecovery = handleServiceRecovery;
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.observabilityGateway = observabilityGateway;
        this.serviceEventPublisher = serviceEventPublisher;
    }

    public void handle(MonitoredService service, boolean isUp){
        ServiceStateChange change;

        if(isUp){
            change = service.changeStatusToUp();
            if(change == ServiceStateChange.UP_CONFIRMED) {
                handleServiceRecovery.resolve(service);
                observabilityGateway.recordServiceUp(service.Name());
                serviceEventPublisher.PublishRecovered(
                        new ServiceOnlineEvent(service.Id(), service.Name(),LocalDateTime.now()));
            }
        }else{
            change = service.changeStatusToDown();
            if(change == ServiceStateChange.DOWN_CONFIRMED) {
                handleServiceDown.serviceIsDown(service);
                observabilityGateway.recordServiceDown(service.Name());
                serviceEventPublisher.PublishOffline(
                        new ServiceOfflineEvent(service.Id(), service.Name(), LocalDateTime.now())
                );
            }
        }
        monitoredServiceRepository.save(service);
    }
}
