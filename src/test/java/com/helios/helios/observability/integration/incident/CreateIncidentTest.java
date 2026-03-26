package com.helios.helios.observability.integration.incident;

import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class CreateIncidentTest {
    @Mock
    private IncidentRepository repository;

    @InjectMocks
    private CreateIncident useCase;

    private MonitoredService createService() {
        return MonitoredService.createNew(
                "Service",
                "http://test.com",
                SLAServiceEnum.URGENT
        );
    }

    private Alert createAlert(MonitoredService service) {
        return Alert.createNew(service, com.helios.helios.observability.core.domain.alert.AlertType.DOWN);
    }

    @Test
    void shouldCreateIncident() {
        // given
        MonitoredService service = createService();
        Alert alert = createAlert(service);

        IncidentSeverity severity = IncidentSeverity.HIGH;

        when(repository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // when
        Incident result = useCase.createIncident(service, alert, severity);

        // then
        assertNotNull(result);
        assertEquals(service, result.service());
        assertEquals(severity, result.severity());

        verify(repository, times(1)).save(result);
    }
}
