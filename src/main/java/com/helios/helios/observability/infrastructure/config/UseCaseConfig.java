package com.helios.helios.observability.infrastructure.config;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl;

    public UseCaseConfig(MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl) {
        this.monitoredServiceRepositoryImpl = monitoredServiceRepositoryImpl;
    }

    @Bean
    public RegisterMonitoredService registerMonitoredServiceUseCaseConfig(){
        return new RegisterMonitoredService(monitoredServiceRepositoryImpl);
    }

}
