package com.helios.helios.observability.infrastructure.dto.alert;

import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;

import java.time.LocalDateTime;

public record AlertResponseDTO(Long id,
                             String service,
                             LocalDateTime createdAt,
                             LocalDateTime solvedAt,
                             AlertType type
   ) {
}
