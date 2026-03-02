package com.helios.helios.observability.infrastructure.config;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.application.service.usecases.orquestrator.CheckServiceHealth;
import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceDown;
import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceRecovery;
import com.helios.helios.observability.application.service.usecases.orquestrator.ServiceHealthHandler;
import com.helios.helios.observability.infrastructure.healthcheck.HealthCheckGatewayImpl;
import com.helios.helios.observability.infrastructure.observability.adapter.PrometheusObservabilityGatewayImpl;
import com.helios.helios.observability.infrastructure.persistency.implementations.AlertRepositoryImpl;
import com.helios.helios.observability.infrastructure.persistency.implementations.IncidentRepositoryImpl;
import com.helios.helios.observability.infrastructure.persistency.implementations.MonitoredServiceRepositoryImpl;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaAlertRepository;
import com.helios.helios.observability.infrastructure.persistency.repositories.JpaIncidentRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    private final MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl;
    private final HealthCheckGatewayImpl healthCheckGateway;
    private final PrometheusObservabilityGatewayImpl observabilityGateway;
    private final AlertRepositoryImpl alertRepository;
    private final IncidentRepositoryImpl incidentRepository;



    public UseCaseConfig(MonitoredServiceRepositoryImpl monitoredServiceRepositoryImpl, HealthCheckGatewayImpl healthCheckGateway, PrometheusObservabilityGatewayImpl observabilityGateway, AlertRepositoryImpl alertRepository, IncidentRepositoryImpl incidentRepository) {
        this.monitoredServiceRepositoryImpl = monitoredServiceRepositoryImpl;
        this.healthCheckGateway = healthCheckGateway;
        this.observabilityGateway = observabilityGateway;
        this.alertRepository = alertRepository;
        this.incidentRepository = incidentRepository;
    }

    @Bean
    public RegisterMonitoredService registerMonitoredServiceUseCaseConfig(){
        return new RegisterMonitoredService(monitoredServiceRepositoryImpl);
    }

    @Bean
    public CreateAlert createAlertUseCaseConfig(){
        return new CreateAlert(alertRepository);
    }

    @Bean
    public ResolveAlert resolveAlertUseCaseConfig(){
        return new ResolveAlert(alertRepository);
    }

    @Bean
    public CreateIncident createIncidentUseCaseConfig(){
        return new CreateIncident(incidentRepository);
    }

    @Bean
    public FinishIncident finishIncidentUseCaseConfig(){
        return new FinishIncident(incidentRepository);
    }

    @Bean
    public CheckServiceHealth checkServiceHealthUseCaseConfig(ServiceHealthHandler serviceHealthHandler){
        return new CheckServiceHealth(monitoredServiceRepositoryImpl,
                healthCheckGateway,
                serviceHealthHandler,
                observabilityGateway);
    }

    @Bean
    public HandleServiceRecovery handleServiceRecoveryUseCaseConfig(FinishIncident finishIncident,
                                                                    ResolveAlert resolveAlert){
        return new HandleServiceRecovery(monitoredServiceRepositoryImpl, finishIncident, resolveAlert, observabilityGateway);
    }

    @Bean
    public HandleServiceDown handleServiceDownUseCaseConfig(CreateIncident createIncident,
                                                            CreateAlert createAlert){
        return new HandleServiceDown(monitoredServiceRepositoryImpl,createIncident, createAlert, alertRepository, observabilityGateway);
    }

    @Bean
    public ServiceHealthHandler serviceHealthHandlerUseCaseConfig(HandleServiceDown handleServiceDown,
                                                                  HandleServiceRecovery handleServiceRecovery){
        return new ServiceHealthHandler(handleServiceDown,
                                        handleServiceRecovery);
    }
}
