package com.helios.helios.observability.infrastructure.observability.client;

import io.micrometer.core.instrument.MeterRegistry;

public class PrometheusMonitoringClient implements MonitoringClient{

    private final MeterRegistry meterRegistry;

    public PrometheusMonitoringClient(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void incrementCounter(String name, String service) {
        meterRegistry.counter(name, "Service", service).increment();
    }
}
