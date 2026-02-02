package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.dto.CreateMonitoredServiceRequest;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
public class MonitoredServiceController {

    private final MonitoredServiceRepositoryImpl monitoredServiceRepository;
    private final RegisterMonitoredService registerMonitoredService;

    public MonitoredServiceController(MonitoredServiceRepositoryImpl monitoredServiceRepository, RegisterMonitoredService registerMonitoredService) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.registerMonitoredService = registerMonitoredService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveService(@RequestBody CreateMonitoredServiceRequest service){
        registerMonitoredService.registerNewMonitoredService(service.serviceName(), service.monitoredEndpoint(), service.sla());
        return ResponseEntity.status(HttpStatus.CREATED).body("Service saved and ready to monitoring");
    }

    @GetMapping(value = "/{serviceName}")
    public ResponseEntity<MonitoredService> getServiceInfo(@PathVariable String serviceName){
        return monitoredServiceRepository.findServiceByName(serviceName)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());

    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<MonitoredService>> listAllServices(){
        List<MonitoredService> services = monitoredServiceRepository.listAllServices();
        return ResponseEntity.ok(services);
    }

}
