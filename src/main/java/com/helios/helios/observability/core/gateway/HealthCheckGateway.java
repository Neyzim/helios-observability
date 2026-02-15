package com.helios.helios.observability.core.gateway;

public interface HealthCheckGateway {


    boolean isServiceUp(String url);

    boolean isServiceDown(String url);
}
