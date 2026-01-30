package com.helios.helios.observability.infrastructure.persistency.implementations;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import com.helios.helios.observability.infrastructure.mapper.EntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaMonitoredServiceRepository;

import java.util.List;
import java.util.Optional;

public class MonitoredServiceRepositoryImp implements MonitoredServiceRepository {

    private final JpaMonitoredServiceRepository monitoredServiceRepository;
    private final EntitiesMapper mapper;

    public MonitoredServiceRepositoryImp(JpaMonitoredServiceRepository monitoredServiceRepository, EntitiesMapper mapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(MonitoredService service) {
        monitoredServiceRepository.save(service);
    }

    @Override
    public Optional<MonitoredService> findServiceById(Long Id) {
        Optional<MonitoredService> service =  monitoredServiceRepository.findById(Id);
        return service;
    }

    @Override
    public List<MonitoredService> listMonitoredServicesForStatus(StatusEnum status) {
        return List.of(monitoredServiceRepository.findAllServicesByStatus(status));
    }

    @Override
    public Optional<MonitoredService> findServiceByName(String name) {
        return Optional.of(monitoredServiceRepository.findByServiceName(name));
    }
}
