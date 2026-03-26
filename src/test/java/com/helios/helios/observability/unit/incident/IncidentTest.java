package com.helios.helios.observability.unit.incident;

import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.exception.IncidentAlreadySolved;
import com.helios.helios.observability.core.exception.ServiceCantBeEmpty;
import com.helios.helios.observability.core.exception.SeverityCantBeEmpty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IncidentTest {

    @Test
    void shouldStartWithStartedAtValue(){
        Incident incident = Incident.createNew(
                MonitoredService.createNew("Incident Started at value", "Incident Star at value url", SLAServiceEnum.URGENT),
                IncidentSeverity.DISASTER
        );
        assertNotNull(incident.startedAt());
    }

    @Test
    void shouldThrowExceptionWhenServiceIsNull(){
        assertThrows(ServiceCantBeEmpty.class, () -> {
            Incident.createNew(null, IncidentSeverity.HIGH);
        });
    }

    @Test
    void shouldNotInitiateWithFinishedAtValue(){
        Incident incident = Incident.createNew(
                MonitoredService.createNew("Service Incident Test",
                        "Service Incident Test url",
                        SLAServiceEnum.NORMAL),
                IncidentSeverity.HIGH
        );

        assertNull(incident.finishedAt());
    }

    @Test
    void shouldThrowExceptionWhenFinishingAlreadyFinishedIncident(){
        Incident incident = Incident.createNew(
                MonitoredService.createNew("Service Incident Test",
                        "Service Incident Test url",
                        SLAServiceEnum.NORMAL),
                IncidentSeverity.HIGH
        );

        incident.finish();

        assertThrows(IncidentAlreadySolved.class, incident::finish);
    }

    @Test
    void shouldSetFinishedAtWhenFinishing(){
        Incident incident = Incident.createNew(
                MonitoredService.createNew("Service Incident Test",
                        "Service Incident Test url",
                        SLAServiceEnum.NORMAL),
                IncidentSeverity.HIGH
        );

        incident.finish();

        assertNotNull(incident.finishedAt());
    }

    @Test
    void shouldThrowExceptionWhenSeverityIsNull() {
        assertThrows(SeverityCantBeEmpty.class, () -> {
            Incident.createNew(
                    MonitoredService.createNew("test",
                            "url",
                            SLAServiceEnum.NORMAL),
                    null
            );
        });
    }
}
