package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.dto.incident.IncidentResponseDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class IncidentDtoMapper {


    public IncidentResponseDto toDto(Incident incident){
        if(incident == null){
            return null;
        }
        return new IncidentResponseDto(
                incident.id(),
                incident.service(),
                incident.startedAt(),
                incident.finishedAt(),
                incident.severity()
        );
    }
    public List<IncidentResponseDto> listToDto(List<Incident> incidents){
        if(incidents == null ){
            return null;
        }
        List<IncidentResponseDto> responseDtos = new ArrayList<>();
        for(Incident incident : incidents){
            IncidentResponseDto dto = new IncidentResponseDto(
                    incident.id(),
                    incident.service(),
                    incident.startedAt(),
                    incident.finishedAt(),
                    incident.severity()
            );
            responseDtos.add(dto);
        }
        return responseDtos;
    }
}
