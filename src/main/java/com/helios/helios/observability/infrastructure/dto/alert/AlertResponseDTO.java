package com.helios.helios.observability.infrastructure.dto.alert;

import com.helios.helios.observability.core.domain.alert.AlertType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response representing a Alert")
public record AlertResponseDTO(
        @Schema(description = "Unique identifier of an Alert", example = "5")
        Long id,
        @Schema(description = "Monitored Service", example = "Facebook")
        String service,
        @Schema(description = "Date and hour an Alert has been created", example = "22/10/2025 22:13:12")
        LocalDateTime createdAt,
        @Schema(description = "Date and hour an Alert was resolved", example = "23/10/2025 12:05:14")
        LocalDateTime solvedAt,
        @Schema(description = "Type of a alert", example = "DOWNc")
        AlertType type
   ) {
}
