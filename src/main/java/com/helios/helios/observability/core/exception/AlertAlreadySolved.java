package com.helios.helios.observability.core.exception;

public class AlertAlreadySolved extends RuntimeException {
    public AlertAlreadySolved(String message) {
        super(message);
    }
}
