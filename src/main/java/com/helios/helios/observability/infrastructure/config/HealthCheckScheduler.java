package com.helios.helios.observability.infrastructure.config;

import com.helios.helios.observability.application.service.usecases.orquestrator.CheckServiceHealth;
import org.springframework.scheduling.annotation.Scheduled;

public class HealthCheckScheduler {

    private final CheckServiceHealth checkServiceHealth;

    public HealthCheckScheduler(CheckServiceHealth checkServiceHealth) {
        this.checkServiceHealth = checkServiceHealth;
    }

    @Scheduled(fixedRate = 30000)
    public void run() {
        checkServiceHealth.execute();
    }
}
