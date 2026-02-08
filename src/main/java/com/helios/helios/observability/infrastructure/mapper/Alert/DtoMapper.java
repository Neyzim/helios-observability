package com.helios.helios.observability.infrastructure.mapper.Alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.dto.alert.AlertResponseDTO;

public class DtoMapper {

    public AlertResponseDTO toResponseDto(Alert alert){
        return new AlertResponseDTO(
                alert.id(),
                alert.Service(),
                alert.CreatedAt(),
                alert.SolvedAt(),
                alert.Type()
        );
    }
}
