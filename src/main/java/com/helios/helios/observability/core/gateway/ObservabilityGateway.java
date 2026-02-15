package com.helios.helios.observability.core.gateway;

public interface ObservabilityGateway {

    void recordServiceUp(String serviceName);

    void recordServiceDown(String serviceName);

}
