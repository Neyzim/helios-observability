package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.infrastructure.dto.alert.AlertResponseDTO;
import com.helios.helios.observability.infrastructure.mapper.Alert.AlertDtoMapper;
import com.helios.helios.observability.infrastructure.persistency.implementations.AlertRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/alert")
public class AlertController {

    private final AlertRepositoryImpl alertRepository;
    private final AlertDtoMapper mapper;


    public AlertController(AlertRepositoryImpl alertRepository, AlertDtoMapper mapper) {
        this.alertRepository = alertRepository;
        this.mapper = mapper;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AlertResponseDTO> findAlertById(@PathVariable Long id){
        Alert alert = alertRepository.findAlertById(id).orElseThrow();
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toResponseDto(alert));
    }

    @GetMapping(value = "/unsolved")
    public ResponseEntity<List<AlertResponseDTO>> listAlertsUnsolved(){
        List<Alert> alerts = alertRepository.findUnsolvedAlerts();
        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }

    @GetMapping(value = "/incident/{id}/alerts")

    //não esta criando a relação alert e incident TODO
    public ResponseEntity<List<AlertResponseDTO>> listAlertsByIncidentId(@PathVariable Long id){
        List<Alert> alerts = alertRepository.findAlertsByIncidentId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }

    @GetMapping(value = "/service/{id}/alerts")
    public ResponseEntity<List<AlertResponseDTO>> listAlertsByServiceId(@PathVariable Long id){
        List<Alert> alerts = alertRepository.findAlertsByServiceId(id);

        return ResponseEntity.status(HttpStatus.FOUND).body(mapper.toListResponseDto(alerts));
    }
}
