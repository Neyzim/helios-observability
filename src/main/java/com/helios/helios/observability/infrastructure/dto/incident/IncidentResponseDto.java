package com.helios.helios.observability.infrastructure.dto.incident;

import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response representing a Incident")
public record IncidentResponseDto(
        @Schema(description = "Unique identifier of an Alert", example = "1")
        Long id,
        @Schema(description = "Monitored Service", example = "Google DNS")
        MonitoredService service,
        @Schema(description = "Date and hour an incident has been created", example = "22/10/2025 22:13:12")
        LocalDateTime startedAt,
        @Schema(description = "Date and hour an incident was resolved", example = "23/10/2025 12:05:14")
        LocalDateTime finishedAt,
        @Schema(description = "Severity of a Incident", example = "LOW")
        IncidentSeverity severity) {
}
