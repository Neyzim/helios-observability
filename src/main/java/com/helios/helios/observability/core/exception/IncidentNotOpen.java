package com.helios.helios.observability.core.exception;

public class IncidentNotOpen extends RuntimeException {
    public IncidentNotOpen(String message) {
        super(message);
    }
}
