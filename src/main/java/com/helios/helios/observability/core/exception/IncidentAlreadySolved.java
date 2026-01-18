package com.helios.helios.observability.core.exception;

public class IncidentAlreadySolved extends RuntimeException {
    public IncidentAlreadySolved(String message) {
        super(message);
    }
}
