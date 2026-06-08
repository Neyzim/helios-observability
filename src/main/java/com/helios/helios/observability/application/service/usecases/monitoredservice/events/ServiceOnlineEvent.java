package com.helios.helios.observability.application.service.usecases.monitoredservice.events;

import java.time.LocalDateTime;

public record ServiceOnlineEvent(
        Long serviceId,
        String serviceName,
        LocalDateTime startedAt
) {
}
