package com.helios.helios.observability.infrastructure.mapper.Alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.dto.alert.AlertResponseDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AlertDtoMapper {

    public AlertResponseDTO toResponseDto(Alert alert){
        if(alert == null){
            return null;
        }
        return new AlertResponseDTO(
                alert.id(),
                alert.Service().Name(),
                alert.CreatedAt(),
                alert.SolvedAt(),
                alert.Type()
        );
    }

    public List<AlertResponseDTO> toListResponseDto(List<Alert> alerts) {
        if(alerts == null){
            return null;
        }
        List<AlertResponseDTO> alertResponseDTOS = new ArrayList<>();
        for (Alert alert : alerts) {
            AlertResponseDTO alertResponseDTO = new AlertResponseDTO(
                    alert.id(),
                    alert.Service().Name(),
                    alert.CreatedAt(),
                    alert.SolvedAt(),
                    alert.Type()
            );
            alertResponseDTOS.add(alertResponseDTO);
        }
        return alertResponseDTOS;
    }
}
