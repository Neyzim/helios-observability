package com.helios.helios.observability.infrastructure.config;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.application.service.usecases.orquestrator.CheckServiceHealth;
import com.helios.helios.observability.infrastructure.healthcheck.HealthCheckGatewayImpl;
import com.helios.helios.observability.infrastructure.observability.adapter.PrometheusObservabilityGatewayImpl;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl;
    private final HealthCheckGatewayImpl healthCheckGateway;
    private final MonitoredServiceRepositoryImpl monitoredServiceRepository;
    private final PrometheusObservabilityGatewayImpl prometheusObservabilityGateway;

    public UseCaseConfig(MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl, HealthCheckGatewayImpl healthCheckGateway, MonitoredServiceRepositoryImpl monitoredServiceRepository, PrometheusObservabilityGatewayImpl prometheusObservabilityGateway) {
        this.monitoredServiceRepositoryImpl = monitoredServiceRepositoryImpl;
        this.healthCheckGateway = healthCheckGateway;
        this.monitoredServiceRepository = monitoredServiceRepository;
        this.prometheusObservabilityGateway = prometheusObservabilityGateway;
    }

    @Bean
    public RegisterMonitoredService registerMonitoredServiceUseCaseConfig(){
        return new RegisterMonitoredService(monitoredServiceRepositoryImpl);
    }

    @Bean
    public CheckServiceHealth checkServiceHealthUseCaseConfig(){
        return new CheckServiceHealth(monitoredServiceRepository, healthCheckGateway, prometheusObservabilityGateway);
    }

}
