package com.helios.helios.observability.core.exception;

public class AlertAlreadySolved extends RuntimeException {
    public AlertAlreadySolved(String message) {
        super(message);
    }

    public AlertAlreadySolved(Throwable t) {
        super(t);
    }
}
