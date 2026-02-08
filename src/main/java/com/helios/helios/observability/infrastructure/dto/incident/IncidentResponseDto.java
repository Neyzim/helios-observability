package com.helios.helios.observability.infrastructure.dto.incident;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;

import java.time.LocalDateTime;
import java.util.List;

public record IncidentResponseDto(Long id,
                                MonitoredService service,
                                LocalDateTime startedAt,
                                LocalDateTime finishedAt,
                                IncidentSeverity severity) {
}
