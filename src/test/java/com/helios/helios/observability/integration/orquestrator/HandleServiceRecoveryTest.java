package com.helios.helios.observability.integration.orquestrator;


import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceRecovery;
import com.helios.helios.observability.core.exception.ServiceNotFound;
import static org.junit.jupiter.api.Assertions.*;
import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class HandleServiceRecoveryTest {

    @Mock
    private MonitoredServiceRepository repository;

    @Mock
    private FinishIncident finishIncident;

    @Mock
    private ResolveAlert resolveAlert;

    @Mock
    private ObservabilityGateway observabilityGateway;

    @InjectMocks
    private HandleServiceRecovery useCase;

    @Test
    void shouldHandleRecoveryWhenUpConfirmed() {
        // given
        Long serviceId = 1L;

        MonitoredService service = mock(MonitoredService.class);

        when(repository.findServiceById(serviceId))
                .thenReturn(Optional.of(service));

        when(service.changeStatusToUp())
                .thenReturn(ServiceStateChange.UP_CONFIRMED);

        when(service.Name()).thenReturn("Service A");

        // when
        useCase.resolve(serviceId);

        // then
        verify(observabilityGateway).recordServiceUp("Service A");
        verify(resolveAlert).resolve(serviceId);
        verify(finishIncident).finish(serviceId);

        verify(repository).save(service);
    }


    @Test
    void shouldOnlySaveServiceWhenNotUpConfirmed() {
        Long serviceId = 1L;

        MonitoredService service = mock(MonitoredService.class);

        when(repository.findServiceById(serviceId))
                .thenReturn(Optional.of(service));

        when(service.changeStatusToUp())
                .thenReturn(ServiceStateChange.DOWN_CONFIRMED);

        useCase.resolve(serviceId);

        verifyNoInteractions(observabilityGateway);
        verifyNoInteractions(resolveAlert);
        verifyNoInteractions(finishIncident);

        verify(repository).save(service);
    }



    @Test
    void shouldThrowWhenServiceNotFound() {
        Long serviceId = 1L;

        when(repository.findServiceById(serviceId))
                .thenReturn(Optional.empty());

        assertThrows(ServiceNotFound.class, () -> {
            useCase.resolve(serviceId);
        });

        verifyNoInteractions(resolveAlert);
        verifyNoInteractions(finishIncident);
    }
}