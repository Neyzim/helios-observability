package com.helios.helios.observability.infrastructure.persistency.monitoredservice.implementations;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import com.helios.helios.observability.infrastructure.mapper.EntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.monitoredservice.entities.MonitoredServiceEntity;
import com.helios.helios.observability.infrastructure.persistency.monitoredservice.repositories.JpaMonitoredServiceRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public class MonitoredServiceRepositoryImpl implements MonitoredServiceRepository {

    private final JpaMonitoredServiceRepository monitoredServiceRepository;
    private final EntitiesMapper mapper;

    public MonitoredServiceRepositoryImpl(JpaMonitoredServiceRepository monitoredServiceRepository, EntitiesMapper mapper) {
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.mapper = mapper;
    }

    @Override
    public MonitoredService save(MonitoredService service) {
        MonitoredServiceEntity serviceEntity = mapper.toInfraEntity(service);
        MonitoredServiceEntity saved = monitoredServiceRepository.save(serviceEntity);
        return mapper.toCoreEntity(saved);
    }

    @Override
    public Optional<MonitoredService> findServiceById(Long Id) {
        return monitoredServiceRepository.findById(Id).map(mapper::toCoreEntity);
    }

    @Override
    public List<MonitoredService> listMonitoredServicesForStatus(StatusEnum status) {
        return monitoredServiceRepository.findAllServicesByStatus(status)
                .stream()
                .map(mapper::toCoreEntity)
                .toList();
    }

    @Override
    public Optional<MonitoredService> findServiceByName(String name) {
        return Optional.ofNullable(monitoredServiceRepository.findByServiceName(name))
                                    .map(mapper::toCoreEntity);
    }

    public List<MonitoredService> listAllServices(){
        List<MonitoredServiceEntity> services = monitoredServiceRepository.findAll();
        return services.stream()
                .map(mapper::toCoreEntity)
                .toList();
    }

    public void deleteServiceById(Long id){
        monitoredServiceRepository.deleteById(id);
    }
}
