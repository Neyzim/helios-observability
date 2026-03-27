package com.helios.helios.observability.integration.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceDown;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class HandleServiceDownTest {


    @Mock
    private CreateIncident createIncident;

    @Mock
    private CreateAlert createAlert;

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private HandleServiceDown useCase;

    @Test
    void shouldHandleDownConfirmedFlow() {
        // given

        MonitoredService service = mock(MonitoredService.class);



        when(service.Sla()).thenReturn(com.helios.helios.observability.core.domain.service.SLAServiceEnum.URGENT);
        when(service.Id()).thenReturn(1L);

        Alert createdAlert = mock(Alert.class);
        when(createAlert.createAlert(service, AlertType.DOWN))
                .thenReturn(createdAlert);

        Incident incident = mock(Incident.class);
        when(incident.id()).thenReturn(99L);

        when(createIncident.createIncident(service, createdAlert, IncidentSeverity.from(service.Sla())))
                .thenReturn(incident);

        Alert alert1 = mock(Alert.class);
        Alert alert2 = mock(Alert.class);

        when(alertRepository.findOpenAlertsByServiceId(service.Id()))
                .thenReturn(List.of(alert1, alert2));

        // when
        useCase.serviceIsDown(service);

        // then
        verify(createAlert).createAlert(service, AlertType.DOWN);
        verify(createIncident).createIncident(service, createdAlert, IncidentSeverity.from(service.Sla()));

        verify(alert1).assignIncident(99L);
        verify(alert2).assignIncident(99L);

    }

}