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
    private LocalDateTime createdAt;
    private LocalDateTime solvedAt;
    private AlertType type;

    public Alert (Long id, MonitoredService service, LocalDateTime createdAt, LocalDateTime solvedAt, AlertType type) {
        if (service == null){
            throw new IllegalArgumentException("Alert must have a Service");
        }
        this.id = id;
        this.service = service;
        this.createdAt = LocalDateTime.now();
        this.solvedAt = null;
        this.type = type;
    }

    public void resolve(){
        if (this.solvedAt != null){
            throw new AlertAlreadySolved("Alert already solved");
        }
        this.solvedAt = LocalDateTime.now();
    }
    }

