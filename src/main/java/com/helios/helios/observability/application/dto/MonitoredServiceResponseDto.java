package com.helios.helios.observability.application.dto;

public class MonitoredServiceResponseDto {
    private Long id;
    private String name;
    private String sla;

    public MonitoredServiceResponseDto(Long id, String name, String sla) {
        this.id = id;
        this.name = name;
        this.sla = sla;
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

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }
}
