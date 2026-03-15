package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.infrastructure.dto.incident.IncidentResponseDto;
import com.helios.helios.observability.infrastructure.mapper.incident.IncidentDtoMapper;
import com.helios.helios.observability.infrastructure.persistency.implementations.IncidentRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/incident")
@Tag(name = "Incident", description = "API for managing Incidents")
public class IncidentController {

    private final IncidentRepositoryImpl incidentRepository;
    private final IncidentDtoMapper dtoMapper;


    public IncidentController(IncidentRepositoryImpl incidentRepository, IncidentDtoMapper dtoMapper) {
        this.incidentRepository = incidentRepository;
        this.dtoMapper = dtoMapper;
    }

    @Operation(summary = "List open Incidents of a Service", tags = {"Incidents"})
    @ApiResponses(
            @ApiResponse(responseCode = "302", description = "Incidents Listed")
    )
    @GetMapping("/{serviceId}/incidents")
    public ResponseEntity<List<IncidentResponseDto>> findAllOpenIncidentsByServiceId(@Parameter(description = "Service ID") @PathVariable Long serviceId){
        List<Incident> incident = incidentRepository.findOpenIncidentByServiceId(serviceId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dtoMapper.listToDto(incident));
    }
}
