package com.helios.helios.observability.infrastructure.controllers;

import com.helios.helios.observability.infrastructure.persistency.repositories.JpaIncidentRepository;

public class IncidentController {

    private final JpaIncidentRepository incidentRepository;

    public IncidentController(JpaIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    //TODO
}
