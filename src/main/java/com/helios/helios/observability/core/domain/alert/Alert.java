package com.helios.helios.observability.core.domain.alert;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.exception.AlertAlreadySolved;

import java.time.LocalDateTime;

public class Alert {
    /*
    -INVARIANTS
        * An Alert always have a createdAt value.
        * An alert always have a Service.
        * An alert is never born with a solvedAt value.
        * An alert can only be solved if the solvedAt is empty.
        * If solvedAt has a value, an Alert is already resolved.
        * An alert only can be resolved once.
        * An resolved alert can never be opened again.
        * An alert always have a type.
     */
    private Long id;
    private MonitoredService service;
    private LocalDateTime createdAt = LocalDateTime.now();;
    private LocalDateTime solvedAt;
    private AlertType type;

    public static Alert createNew (MonitoredService service, AlertType type) {
        Alert alert = new Alert();
        if (service == null){
            throw new IllegalArgumentException("Alert must have a Service");
        }
        alert.service = service;
        alert.type = type;
        return alert;
    }

    public static Alert rehydrate(Long id, MonitoredService service, LocalDateTime createdAt, LocalDateTime solvedAt, AlertType type) {
        Alert alert = new Alert();
        alert.id = id;
        alert.service = service;
        alert.createdAt = createdAt;
        alert.solvedAt = solvedAt;
        alert.type = type;
        return alert;
    }

    public void resolve(){
        if (this.solvedAt != null){
            throw new AlertAlreadySolved("Alert already solved");
        }
        this.solvedAt = LocalDateTime.now();
    }

    public Long id() {
        return id;
    }

    public MonitoredService Service() {
        return service;
    }

    public LocalDateTime CreatedAt() {
        return createdAt;
    }

    public LocalDateTime SolvedAt() {
        return solvedAt;
    }

    public AlertType Type() {
        return type;
    }
}

