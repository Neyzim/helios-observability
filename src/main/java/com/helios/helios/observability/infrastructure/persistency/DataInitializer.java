package com.helios.helios.observability.infrastructure.persistency;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import com.helios.helios.observability.infrastructure.mapper.MonitoredService.MonitoredServiceEntitiesMapper;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer {
    /*
    This class is used to initialize Data when Api is running
     */

    private final MonitoredServiceRepositoryImpl repository;
    private final MonitoredServiceEntitiesMapper mapper;


    public DataInitializer(MonitoredServiceRepositoryImpl repository, MonitoredServiceEntitiesMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public void upsertService(String name, String endpoint){
        Optional<MonitoredService> existing = repository.findServiceByName(name);

        MonitoredServiceEntity entity = new MonitoredServiceEntity(
                existing.map(MonitoredService::Id).orElse(null),
                name,
                endpoint,
                null,
                SLAServiceEnum.NORMAL,
                null,
                null
        );
        repository.save(mapper.toCoreEntity(entity));
    }

    @PostConstruct
    public void init(){
        upsertService("Healthy service (Google)", "https://www.google.com");
        upsertService("Always offline Service", "Service Offline");
    }
}
