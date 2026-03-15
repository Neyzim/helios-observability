package com.helios.helios.observability.infrastructure.dto.monitoredservice;

import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Request to create a service to be monitored")
public record CreateMonitoredServiceRequest (
        @Schema(description = "Service name", example = "Google DNS")
        String serviceName,
        @Schema(description = "Service Endpoint to be monitored", example = "https://www.google.com")
        String monitoredEndpoint,
        @Schema(description = "Expected SLA for the service",
                example = "DISASTER")
        SLAServiceEnum sla){

}