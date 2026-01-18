package com.helios.helios.observability.core.exception;

public class ServiceAlreadyDown extends RuntimeException {

    public ServiceAlreadyDown(String message) {
        super(message);
    }
    public ServiceAlreadyDown(Throwable t) {
        super(t);
    }
}
