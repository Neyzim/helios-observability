package com.helios.helios.observability.infrastructure.persistency.entities;

import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "monitored_service")
public class MonitoredServiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String monitoredEndpoint;
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private SLAServiceEnum sla;

    public MonitoredServiceEntity(Long id, String name, String monitoredEndpoint, StatusEnum status, SLAServiceEnum sla) {
        this.id = id;
        this.name = name;
        this.monitoredEndpoint = monitoredEndpoint;
        this.status = status;
        this.sla = sla;
    }

    public MonitoredServiceEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMonitoredEndpoint() {
        return monitoredEndpoint;
    }

    public void setMonitoredEndpoint(String monitoredEndpoint) {
        this.monitoredEndpoint = monitoredEndpoint;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public SLAServiceEnum getSla() {
        return sla;
    }

    public void setSla(SLAServiceEnum sla) {
        this.sla = sla;
    }
}
