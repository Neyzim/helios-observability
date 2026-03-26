package com.helios.helios.observability.integration.orquestrator;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.application.service.usecases.incident.CreateIncident;
import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceDown;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.domain.incident.IncidentSeverity;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.exception.ServiceNotFound;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.AlertRepository;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class HandleServiceDownTest {

    @Mock
    private MonitoredServiceRepository serviceRepository;

    @Mock
    private CreateIncident createIncident;

    @Mock
    private CreateAlert createAlert;

    @Mock
    private AlertRepository alertRepository;

    @Mock
    private ObservabilityGateway observabilityGateway;

    @InjectMocks
    private HandleServiceDown useCase;

    @Test
    void shouldHandleDownConfirmedFlow() {
        // given
        Long serviceId = 1L;

        MonitoredService service = mock(MonitoredService.class);
        when(serviceRepository.findServiceById(serviceId))
                .thenReturn(Optional.of(service));

        when(service.changeStatusToDown())
                .thenReturn(ServiceStateChange.DOWN_CONFIRMED);

        when(service.Name()).thenReturn("Service A");
        when(service.Sla()).thenReturn(com.helios.helios.observability.core.domain.service.SLAServiceEnum.URGENT);
        when(service.Id()).thenReturn(serviceId);

        Alert createdAlert = mock(Alert.class);
        when(createAlert.createAlert(service, AlertType.DOWN))
                .thenReturn(createdAlert);

        Incident incident = mock(Incident.class);
        when(incident.id()).thenReturn(99L);

        when(createIncident.createIncident(service, createdAlert, IncidentSeverity.from(service.Sla())))
                .thenReturn(incident);

        Alert alert1 = mock(Alert.class);
        Alert alert2 = mock(Alert.class);

        when(alertRepository.findOpenAlertsByServiceId(serviceId))
                .thenReturn(List.of(alert1, alert2));

        // when
        useCase.serviceIsDown(serviceId);

        // then
        verify(observabilityGateway).recordServiceDown("Service A");

        verify(createAlert).createAlert(service, AlertType.DOWN);
        verify(createIncident).createIncident(service, createdAlert, IncidentSeverity.from(service.Sla()));

        verify(alert1).assignIncident(99L);
        verify(alert2).assignIncident(99L);

        verify(alertRepository).save(alert1);
        verify(alertRepository).save(alert2);

        verify(serviceRepository).save(service);
    }

    @Test
    void shouldOnlySaveServiceWhenNotDownConfirmed() {
        Long serviceId = 1L;

        MonitoredService service = mock(MonitoredService.class);

        when(serviceRepository.findServiceById(serviceId))
                .thenReturn(Optional.of(service));

        when(service.changeStatusToDown())
                .thenReturn(ServiceStateChange.NO_CHANGE);

        useCase.serviceIsDown(serviceId);

        verifyNoInteractions(observabilityGateway);
        verifyNoInteractions(createAlert);
        verifyNoInteractions(createIncident);
        verifyNoInteractions(alertRepository);

        verify(serviceRepository).save(service);
    }

    @Test
    void shouldThrowWhenServiceNotFound() {
        Long serviceId = 1L;

        when(serviceRepository.findServiceById(serviceId))
                .thenReturn(Optional.empty());

        assertThrows(ServiceNotFound.class, () -> {
            useCase.serviceIsDown(serviceId);
        });

        verifyNoInteractions(createAlert);
        verifyNoInteractions(createIncident);
        verifyNoInteractions(alertRepository);
    }
}