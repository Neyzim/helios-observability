package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.CreateMonitoredServiceRequest;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.ResponseMonitoredSeviceDto;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceDtoMapper;
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
    private final MonitoredServiceDtoMapper monitoredServiceDtoMapper;

    public MonitoredServiceController(MonitoredServiceRepositoryImpl monitoredServiceRepository, RegisterMonitoredService registerMonitoredService, MonitoredServiceDtoMapper monitoredServiceDtoMapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.registerMonitoredService = registerMonitoredService;
        this.monitoredServiceDtoMapper = monitoredServiceDtoMapper;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<?> saveService(@RequestBody CreateMonitoredServiceRequest service){
        MonitoredService savedService = registerMonitoredService.registerNewMonitoredService(service.serviceName(), service.monitoredEndpoint(), service.sla());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
    }

    @GetMapping(value = "/{serviceName}")
    public ResponseEntity<ResponseMonitoredSeviceDto> getServiceInfo(@PathVariable String serviceName){
        ResponseMonitoredSeviceDto service =  monitoredServiceRepository.findServiceByName(serviceName)
                .map(monitoredServiceDtoMapper::toDto).orElseThrow();
        return ResponseEntity.ok().body(service);
    }

    @GetMapping(value = "/list")
    public ResponseEntity<List<ResponseMonitoredSeviceDto>> listAllServices(){
        List<ResponseMonitoredSeviceDto> services = monitoredServiceRepository.listAllServices()
                .stream()
                .map(monitoredServiceDtoMapper::toDto)
                .toList();
        return ResponseEntity.ok(services);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        monitoredServiceRepository.deleteServiceById(id);
        return ResponseEntity.ok().body("Service Deleted: " + id);
    }

}
