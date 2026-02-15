package com.helios.helios.observability.infrastructure.healthcheck;

import com.helios.helios.observability.core.gateway.HealthCheckGateway;

public class HealthCheckGatewayImpl implements HealthCheckGateway {
    @Override
    public boolean isServiceUp(String url) {
        return false;

        //todo Implement HTTPCLient or somethin alike
    }

    @Override
    public boolean isServiceDown(String url) {
        return false;
    }
}
