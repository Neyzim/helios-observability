package com.helios.helios.observability.infrastructure.observability.client;

public interface MonitoringClient {

    void incrementCounter(String name, String service);

}
