package com.helios.helios.observability.application.service.usecases.orquestrator;

import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOfflineEvent;
import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOnlineEvent;

public interface ServiceEventPublisher {

    void PublishRecovered(ServiceOnlineEvent event);

    void PublishOffline(ServiceOfflineEvent event);
}
