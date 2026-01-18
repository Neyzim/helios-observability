package com.helios.helios.observability.core.exception;

public class ServiceAlreadyUp extends RuntimeException {
    public ServiceAlreadyUp(String message) {
        super(message);
    }

    public ServiceAlreadyUp(Throwable t) {
        super(t);
    }
}
