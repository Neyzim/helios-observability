package com.helios.helios.observability.application.service.usecases.monitoredservice.events;

import java.time.LocalDateTime;

public record ServiceOfflineEvent(
        Long serviceId,
        String serviceName,
        LocalDateTime startedAt
) {
}
