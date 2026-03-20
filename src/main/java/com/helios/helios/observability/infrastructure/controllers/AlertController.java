package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.exception.AlertNotFound;
import com.helios.helios.observability.infrastructure.dto.alert.AlertResponseDTO;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertDtoMapper;
import com.helios.helios.observability.infrastructure.persistency.implementations.AlertRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/alert")
@Tag(name = "Alert" ,description = "API for managing Alerts")
public class AlertController {

    private final AlertRepositoryImpl alertRepository;
    private final AlertDtoMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(AlertController.class);


    public AlertController(AlertRepositoryImpl alertRepository, AlertDtoMapper mapper) {
        this.alertRepository = alertRepository;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Find Alert by ID", tags = {"Alert"})
    @ApiResponses(
            @ApiResponse(responseCode = "302", description = "Alert find")
    )
    public ResponseEntity<AlertResponseDTO> findAlertById(@Parameter(description = "Alert ID")
                                                          @PathVariable Long id){
        Alert alert = alertRepository.findAlertById(id).orElseThrow(() -> new AlertNotFound());
        log.info("GET /alert/{} - fetching alert with id: {}", id, id);
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toResponseDto(alert));
    }

    @Operation(summary = "List alerts unsolved", tags = {"Alert"})
    @ApiResponses(
            @ApiResponse(responseCode = "302", description = "Unsolved alerts listed")
    )
    @GetMapping(value = "/unsolved")
    public ResponseEntity<List<AlertResponseDTO>> listAlertsUnsolved(){
        List<Alert> alerts = alertRepository.findUnsolvedAlerts();
        log.info("GET /alert/unsolved - Listing {} unsolved Alerts", alerts.size());
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }

    @Operation(summary = "List Alerts of an Incident", tags = {"Alert"})
    @ApiResponses(
            @ApiResponse(responseCode = "302", description = "Alerts listed!")
    )
    @GetMapping(value = "/incident/{id}/alerts")
    public ResponseEntity<List<AlertResponseDTO>> listAlertsByIncidentId(@Parameter(description = "Incident ID")
                                                                         @PathVariable Long id){
        List<Alert> alerts = alertRepository.findAlertsByIncidentId(id);
        log.info("GET /alert/incident/{}/alerts - Listing Alerts vinculed to a IncidentID: {}", id, id);
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }

    @Operation(summary = "List alerts from a Service", tags = {"Alert"})
    @ApiResponses(
            @ApiResponse(responseCode = "302", description = "Alerts Listed")
    )
    @GetMapping(value = "/service/{id}/alerts")
    public ResponseEntity<List<AlertResponseDTO>> listAlertsByServiceId(@Parameter(description = "Service ID")
                                                                        @PathVariable Long id){
        List<Alert> alerts = alertRepository.findAlertsByServiceId(id);
        log.info("GET /alert/service/{}/alerts - Listing all alerts of a serviceID: {}",id, id );
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }
}
