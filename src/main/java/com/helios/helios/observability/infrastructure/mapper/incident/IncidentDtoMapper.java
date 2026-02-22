package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.dto.incident.IncidentResponseDto;
import org.springframework.stereotype.Component;

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
}
