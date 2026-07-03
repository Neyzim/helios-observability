package com.helios.helios.observability.application.service.usecases.monitoredservice.events;

import com.helios.helios.observability.core.domain.service.StatusEnum;

import java.time.LocalDateTime;

public record ServiceOfflineEvent(
        Long serviceId,
        String serviceName,
        LocalDateTime startedAt,
        StatusEnum status
) {
}
