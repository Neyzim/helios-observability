package com.helios.helios.observability.infrastructure.persistency.entities;

import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "alert")
public class AlertEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private MonitoredService service;
    private LocalDateTime createdAt;
    private LocalDateTime solvedAt;
    private AlertType type;

    public AlertEntity() {
    }

    public AlertEntity(Long id, MonitoredService service, LocalDateTime createdAt, LocalDateTime solvedAt, AlertType type) {
        this.id = id;
        this.service = service;
        this.createdAt = createdAt;
        this.solvedAt = solvedAt;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonitoredService getService() {
        return service;
    }

    public void setService(MonitoredService service) {
        this.service = service;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getSolvedAt() {
        return solvedAt;
    }

    public void setSolvedAt(LocalDateTime solvedAt) {
        this.solvedAt = solvedAt;
    }

    public AlertType getType() {
        return type;
    }

    public void setType(AlertType type) {
        this.type = type;
    }
}
