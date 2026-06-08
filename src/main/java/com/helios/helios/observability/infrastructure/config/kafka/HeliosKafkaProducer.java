package com.helios.helios.observability.infrastructure.config.kafka;

import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOfflineEvent;
import com.helios.helios.observability.application.service.usecases.monitoredservice.events.ServiceOnlineEvent;
import com.helios.helios.observability.application.service.usecases.orquestrator.ServiceEventPublisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HeliosKafkaProducer implements ServiceEventPublisher {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final String topic = "service-events";

    public HeliosKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void PublishOffline(ServiceOfflineEvent event) {
        kafkaTemplate.send(topic, event);
    }

    @Override
    public void PublishRecovered(ServiceOnlineEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
