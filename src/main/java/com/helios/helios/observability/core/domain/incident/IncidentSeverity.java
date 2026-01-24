package com.helios.helios.observability.core.domain.incident;

import com.helios.helios.observability.core.domain.service.SLAServiceEnum;

public enum IncidentSeverity {
    DISASTER,
    HIGH,
    MEDIUM,
    LOW;

    public static IncidentSeverity from(SLAServiceEnum sla){
        return switch (sla){
            case DISASTER -> DISASTER;
            case URGENT -> HIGH;
            case NORMAL -> MEDIUM;
            case ATTENTION -> LOW;
        };
    }
}
