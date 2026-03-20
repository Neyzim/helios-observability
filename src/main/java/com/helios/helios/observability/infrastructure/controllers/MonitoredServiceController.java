package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.exception.ServiceNotFound;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.CreateMonitoredServiceRequest;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.ResponseMonitoredServiceDto;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceDtoMapper;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/service")
@Tag(name = "Monitored Services", description = "API for managing monitored services")
public class MonitoredServiceController {

    private final MonitoredServiceRepositoryImpl monitoredServiceRepository;
    private final RegisterMonitoredService registerMonitoredService;
    private final MonitoredServiceDtoMapper monitoredServiceDtoMapper;
    private static final Logger log = LoggerFactory.getLogger(MonitoredServiceController.class);

    public MonitoredServiceController(MonitoredServiceRepositoryImpl monitoredServiceRepository, RegisterMonitoredService registerMonitoredService, MonitoredServiceDtoMapper monitoredServiceDtoMapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.registerMonitoredService = registerMonitoredService;
        this.monitoredServiceDtoMapper = monitoredServiceDtoMapper;
    }

    @Operation(summary = "Save a new Service to be monitored", tags = {"Services"})
    @ApiResponses(
            @ApiResponse(responseCode = "201", description = "Service successfully saved")
    )
    @PostMapping(value = "/save")
    public ResponseEntity<ResponseMonitoredServiceDto> saveService(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Service data to be monitored",
                    required = true
            )
            @RequestBody CreateMonitoredServiceRequest service){

        MonitoredService savedService = registerMonitoredService.registerNewMonitoredService(service.serviceName(), service.monitoredEndpoint(), service.sla());
        log.info("POST service/save - Saving a new Monitored Service");
        return ResponseEntity.status(HttpStatus.CREATED).body(monitoredServiceDtoMapper.toDto(savedService));
    }

    @Operation(summary = "Get a service Details", tags = {"Services"})
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Service found"),
            @ApiResponse(responseCode = "404", description = "Service Not Found")
    })
    @GetMapping(value = "/{serviceName}")
    public ResponseEntity<ResponseMonitoredServiceDto> getServiceInfo(@Parameter(description = "Service Name") @PathVariable String serviceName){
        ResponseMonitoredServiceDto service =  monitoredServiceRepository.findServiceByName(serviceName)
                .map(monitoredServiceDtoMapper::toDto).orElseThrow(() -> new ServiceNotFound());
        log.info("GET /service/{} - Getting information of a service: {}",service, service);
        return ResponseEntity.ok().body(service);
    }

    @Operation(summary = "List all saved Services", tags = {"Services"})
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Services listed")
    )
    @GetMapping(value = "/list")
    public ResponseEntity<List<ResponseMonitoredServiceDto>> listAllServices(){
        List<ResponseMonitoredServiceDto> services = monitoredServiceRepository.listAllServices()
                .stream()
                .map(monitoredServiceDtoMapper::toDto)
                .toList();
        log.info("GET /service/list - Listing all services saved");
        return ResponseEntity.ok(services);
    }

    @Operation(summary = "Delete a saved Service", tags = {"Services"} )
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Service Deleted")
    )
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> delete(@Parameter(description = "Service ID")@PathVariable Long id){
        monitoredServiceRepository.deleteServiceById(id);
        log.info("DELETE /service/delete/{} - Deleting Service", id);
        return ResponseEntity.ok().body("Service Deleted: " + id);
    }

}
