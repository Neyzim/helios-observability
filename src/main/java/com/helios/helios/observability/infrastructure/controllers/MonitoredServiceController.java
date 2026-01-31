package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImp;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service")
public class MonitoredServiceController {

    private final MonitoredServiceRepositoryImp monitoredServiceRepository;

    public MonitoredServiceController(MonitoredServiceRepositoryImp monitoredServiceRepository) {
        this.monitoredServiceRepository = monitoredServiceRepository;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> save(@RequestBody MonitoredService service){
        monitoredServiceRepository.save(service);
        return ResponseEntity.status(HttpStatus.CREATED).body("Service saved and ready to monitoring");
    }
}
