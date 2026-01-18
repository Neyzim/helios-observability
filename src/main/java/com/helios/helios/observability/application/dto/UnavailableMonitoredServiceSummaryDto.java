package com.helios.helios.observability.application.dto;

public class UnavailableMonitoredServiceSummaryDto {

    private String name;
    private  String status;
    private String sla;

    public UnavailableMonitoredServiceSummaryDto(String name, String status, String sla) {
        this.name = name;
        this.status = status;
        this.sla = sla;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSla() {
        return sla;
    }

    public void setSla(String sla) {
        this.sla = sla;
    }
}
