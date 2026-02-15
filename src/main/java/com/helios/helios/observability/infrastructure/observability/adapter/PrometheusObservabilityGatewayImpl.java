package com.helios.helios.observability.infrastructure.observability.adapter;

import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PrometheusObservabilityGatewayImpl implements ObservabilityGateway {

    private final MeterRegistry meterRegistry;

    public PrometheusObservabilityGatewayImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Override
    public void recordServiceUp(String serviceName) {
        meterRegistry.counter("service_is_up", "service", serviceName).increment();
    }

    @Override
    public void recordServiceDown(String serviceName) {
        meterRegistry.counter("service_is_down", "service", serviceName).increment();
    }
}
