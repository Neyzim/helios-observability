package com.helios.helios.observability.integration.orquestrator;

import com.helios.helios.observability.application.service.usecases.orquestrator.CheckServiceHealth;
import com.helios.helios.observability.application.service.usecases.orquestrator.ServiceHealthHandler;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.gateway.HealthCheckGateway;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CheckServiceHealthTest {

    @Mock
    private MonitoredServiceRepository repository;

    @Mock
    private HealthCheckGateway healthCheckGateway;

    @Mock
    private ServiceHealthHandler handler;

    @Mock
    private ObservabilityGateway observabilityGateway;

    @InjectMocks
    private CheckServiceHealth useCase;

    @Test
    void shouldCheckAllServicesAndHandleStatus() {
        // given
        MonitoredService service1 = mock(MonitoredService.class);
        MonitoredService service2 = mock(MonitoredService.class);

        when(repository.listAllServices()).thenReturn(List.of(service1, service2));

        when(service1.MonitoredEndpoint()).thenReturn("url1");
        when(service2.MonitoredEndpoint()).thenReturn("url2");


        when(healthCheckGateway.isServiceUp("url1")).thenReturn(true);
        when(healthCheckGateway.isServiceUp("url2")).thenReturn(false);

        // when
        useCase.execute();

        // then
        verify(handler).handle(service1, true);
        verify(handler).handle(service2, false);

    }

    @Test
    void shouldDoNothingWhenNoServices() {
        when(repository.listAllServices()).thenReturn(List.of());

        useCase.execute();

        verifyNoInteractions(healthCheckGateway);
        verifyNoInteractions(handler);
        verifyNoInteractions(observabilityGateway);
    }
}