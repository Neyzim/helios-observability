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
    private List<Alert> alerts;
    private LocalDateTime startedAt = LocalDateTime.now();
    private LocalDateTime finishedAt;
    private IncidentSeverity severity;

    public Incident(
                    MonitoredService service,
                    List<Alert> alerts,
                    IncidentSeverity severity
    ) {
        if (service == null){
            throw new IllegalArgumentException("Incident must have a Service");
        }
        this.service = service;
        if (alerts == null || alerts.isEmpty()){
            throw new IllegalArgumentException("An incident must have at least one Alert");
        }
        this.alerts = alerts;
        if (severity == null){
            throw new IllegalArgumentException("Incident must have a Severity");
        }
        this.severity = severity;
    }

    public void vinculateAlert(Alert alert){
        if (this.finishedAt != null){
            throw new IncidentNotOpen("Its not possible to vinculate an alert to an incident already finished");
        }
        this.alerts.add(alert);
    }

    public void finish(){
        if (this.finishedAt != null){
            throw new IncidentAlreadySolved("Incident already solved");
        }
        this.finishedAt = LocalDateTime.now();
    }
}