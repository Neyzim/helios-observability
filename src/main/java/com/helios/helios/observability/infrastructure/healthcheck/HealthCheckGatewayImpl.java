package com.helios.helios.observability.infrastructure.healthcheck;

import com.helios.helios.observability.core.gateway.HealthCheckGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HealthCheckGatewayImpl implements HealthCheckGateway {

    private final RestTemplate restTemplate;

    public HealthCheckGatewayImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public boolean isServiceUp(String url) {
        try {
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);

            boolean isUp = response.getStatusCode().is2xxSuccessful();

            return new HeathCheckResult(isUp).isUp();
        } catch (Exception e) {
            return new HeathCheckResult(false).isUp();
        }
    }
}
