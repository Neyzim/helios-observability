package com.helios.helios.observability.infrastructure.dto.monitoredservice;

import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response representing a monitored service")
public record ResponseMonitoredServiceDto(
        @Schema(description = "Unique identifier of a Monitored Service", example = "1")
        Long id,
        @Schema(description = "Name of the Monitored Service", example = "Google DNS")
        String serviceName,
        @Schema(description = "Endpoint to be monitored", example = "https://www.google.com")
        String monitoredEndpoint,
        @Schema(description = "Service SLA level", example = "URGENT")
        SLAServiceEnum sla) {
}
