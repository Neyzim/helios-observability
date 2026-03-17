package com.helios.helios.observability.core.exception;

public class IncidentAlreadySolved extends CustomException {
    public IncidentAlreadySolved() {
        super("Incident is already solved");
    }
}
