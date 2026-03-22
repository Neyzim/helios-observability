package com.helios.helios.observability.unit.monitoredservice.monitoredservice;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IncidentTest {
    
    @Test
    void shouldStartWithStartedAtValue(){
        Incident incident = Incident.createNew(
                MonitoredService.createNew("Incident Star at value", "Incident Star at value url", SLAServiceEnum.URGENT),
                IncidentSeverity.DISASTER
        );
        assertEquals(incident.startedAt(), LocalDateTime.now());
    }
}
