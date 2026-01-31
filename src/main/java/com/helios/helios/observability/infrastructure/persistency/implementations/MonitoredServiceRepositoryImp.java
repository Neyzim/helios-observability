package com.helios.helios.observability.infrastructure.persistency.implementations;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import com.helios.helios.observability.infrastructure.mapper.EntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaMonitoredServiceRepository;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Repository
public class MonitoredServiceRepositoryImp implements MonitoredServiceRepository {

    private final JpaMonitoredServiceRepository monitoredServiceRepository;
    private final EntitiesMapper mapper;

    public MonitoredServiceRepositoryImp(JpaMonitoredServiceRepository monitoredServiceRepository, EntitiesMapper mapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(MonitoredService service) {
        MonitoredServiceEntity coreService = mapper.toInfraEntity(service);
        monitoredServiceRepository.save(coreService);
    }

    @Override
    public Optional<MonitoredService> findServiceById(Long Id) {
        return monitoredServiceRepository.findById(Id).map(mapper::toCoreEntity);
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
