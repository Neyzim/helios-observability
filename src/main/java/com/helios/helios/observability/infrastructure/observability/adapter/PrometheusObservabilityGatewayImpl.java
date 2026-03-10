package com.helios.helios.observability.infrastructure.observability.adapter;

import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class PrometheusObservabilityGatewayImpl implements ObservabilityGateway {

    private final MeterRegistry meterRegistry;
    private final ConcurrentHashMap<String, AtomicInteger> serviceStatus = new ConcurrentHashMap<>();

    public PrometheusObservabilityGatewayImpl(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    private AtomicInteger getStatus(String serviceName){
        return serviceStatus.computeIfAbsent(serviceName, name -> {
            AtomicInteger status = new AtomicInteger(1);
            Gauge.builder("helios_service_status", status, AtomicInteger::get)
                    .description("Current status of monitored service (1 = UP, 0 = DOWN)")
                    .tag("service", name)
                    .register(meterRegistry);
            return status;
        });
    }

    @Override
    public void recordServiceUp(String serviceName) {
       getStatus(serviceName).set(1);
    }

    @Override
    public void recordServiceDown(String serviceName) {
        getStatus(serviceName).set(0);
    }
}
