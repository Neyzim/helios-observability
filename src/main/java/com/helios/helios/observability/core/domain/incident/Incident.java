package com.helios.helios.observability.core.domain.incident;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.exception.AlertAlreadySolved;
import com.helios.helios.observability.core.exception.IncidentAlreadySolved;
import com.helios.helios.observability.core.exception.IncidentNotOpen;

import java.time.LocalDateTime;
import java.util.List;

public class Incident {
    /*
    - INVARIANTS:
        * An incident always have a StartedAt value.
        * An incident always have a Service.
        * An incident is never born with a finishedAt value.
        * An incident can only be finished if the finishedAt is empty.
        * If finishedAt has a value, an incident is already finished.
        * An incident only can be resolved once.
        * An resolved incident can never be opened again.
        * An incident need to have a severity.
        * An incident always have a list of alerts.
     */
    private Long id;
    private MonitoredService service;
    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime finishedAt;
    private IncidentSeverity severity;

    public static Incident createNew(
                    MonitoredService service,
                    IncidentSeverity severity
    ) {
        Incident incident = new Incident();
        if (service == null){
            throw new IllegalArgumentException("Incident must have a Service");
        }
        incident.service = service;
        if (severity == null){
            throw new IllegalArgumentException("Incident must have a Severity");
        }
        incident.severity = severity;

        return incident;
    }

    public static Incident rehydrate(LocalDateTime startedAt, Long id, MonitoredService service, LocalDateTime finishedAt, IncidentSeverity severity) {
        Incident incident = new Incident();
        incident.startedAt = startedAt;
        incident.id = id;
        incident.service = service;
        incident.finishedAt = finishedAt;
        incident.severity = severity;

        return incident;
    }

    public void finish(){
        if (this.finishedAt != null){
            throw new IncidentAlreadySolved("Incident already solved");
        }
        this.finishedAt = LocalDateTime.now();
    }

    public Long id() {
        return id;
    }

    public MonitoredService service() {
        return service;
    }

    public LocalDateTime startedAt() {
        return startedAt;
    }

    public LocalDateTime finishedAt() {
        return finishedAt;
    }

    public IncidentSeverity severity() {
        return severity;
    }
}