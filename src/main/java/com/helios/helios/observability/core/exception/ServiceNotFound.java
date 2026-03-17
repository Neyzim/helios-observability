package com.helios.helios.observability.core.exception;

public class ServiceNotFound extends RuntimeException {
    public ServiceNotFound() {

        super("Service not found");
    }
}
