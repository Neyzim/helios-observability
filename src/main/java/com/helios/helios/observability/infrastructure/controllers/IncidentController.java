package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.dto.incident.IncidentResponseDto;
import com.helios.helios.observability.infrastructure.mapper.incident.IncidentDtoMapper;
import com.helios.helios.observability.infrastructure.persistency.implementations.IncidentRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/incident")
public class IncidentController {

    private final IncidentRepositoryImpl incidentRepository;
    private final IncidentDtoMapper dtoMapper;


    public IncidentController(IncidentRepositoryImpl incidentRepository, IncidentDtoMapper dtoMapper) {
        this.incidentRepository = incidentRepository;
        this.dtoMapper = dtoMapper;
    }

    @GetMapping("/{serviceId}/incidents")
    public ResponseEntity<List<IncidentResponseDto>> findAllOpenIncidentsByServiceId(@PathVariable Long serviceId){
        List<Incident> incident = incidentRepository.findOpenIncidentByServiceId(serviceId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dtoMapper.listToDto(incident));
    }
}
