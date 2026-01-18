package com.helios.helios.observability.application.exception;

public class MonitoredServiceNotFound extends RuntimeException {
    public MonitoredServiceNotFound(String message) {
        super(message);
    }
}
