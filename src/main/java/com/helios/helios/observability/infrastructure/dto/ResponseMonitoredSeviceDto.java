package com.helios.helios.observability.infrastructure.dto;

import com.helios.helios.observability.core.domain.service.SLAServiceEnum;

public record ResponseMonitoredSeviceDto(String serviceName,
                                         String monitoredEndpoint,
                                         SLAServiceEnum sla) {
}
