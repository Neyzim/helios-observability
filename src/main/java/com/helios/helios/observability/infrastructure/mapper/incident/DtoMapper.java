package com.helios.helios.observability.infrastructure.mapper.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.dto.incident.IncidentResponseDto;

public class DtoMapper {


    public IncidentResponseDto toDto(Incident incident){
        return new IncidentResponseDto(
                incident.id(),
                incident.service(),
                incident.startedAt(),
                incident.finishedAt(),
                incident.severity()
        );
    }
}
