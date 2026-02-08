package com.helios.helios.observability.infrastructure.persistency.entities;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "incident")
public class IncidentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "service_id")
    private MonitoredServiceEntity service;
    @OneToMany
    private List<AlertEntity> alerts;
    private LocalDateTime startedAt;
    private LocalDateTime finishedAt;
    private IncidentSeverity severity;

    public IncidentEntity(Long id, MonitoredServiceEntity service, List<AlertEntity> alerts, LocalDateTime startedAt, LocalDateTime finishedAt, IncidentSeverity severity) {
        this.id = id;
        this.service = service;
        this.alerts = alerts;
        this.startedAt = startedAt;
        this.finishedAt = finishedAt;
        this.severity = severity;
    }

    public IncidentEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MonitoredServiceEntity getService() {
        return service;
    }

    public void setService(MonitoredServiceEntity service) {
        this.service = service;
    }

    public List<AlertEntity> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<AlertEntity> alerts) {
        this.alerts = alerts;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public LocalDateTime getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(LocalDateTime finishedAt) {
        this.finishedAt = finishedAt;
    }

    public IncidentSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(IncidentSeverity severity) {
        this.severity = severity;
    }
}
