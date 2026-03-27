package com.helios.helios.observability.integration.orquestrator;

import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceDown;
import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceRecovery;
import com.helios.helios.observability.application.service.usecases.orquestrator.ServiceHealthHandler;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.gateway.ObservabilityGateway;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class ServiceHealthHandlerTest {

    @Mock
    private HandleServiceDown handleServiceDown;

    @Mock
    private HandleServiceRecovery handleServiceRecovery;

    @InjectMocks
    private ServiceHealthHandler handler;

    @Mock
    private MonitoredServiceRepository monitoredServiceRepository;

    @Mock
    private ObservabilityGateway observabilityGateway;

    @Test
    void shouldCallRecoveryWhenServiceIsUpAndConfirmed() {
        // given
        MonitoredService service = mock(MonitoredService.class);

        when(service.changeStatusToUp())
                .thenReturn(ServiceStateChange.UP_CONFIRMED);


        // when
        handler.handle(service, true);

        // then
        verify(handleServiceRecovery).resolve(service);
        verifyNoInteractions(handleServiceDown);
    }

    @Test
    void shouldNotTriggerRecoveryWhenUpNotConfirmed() {
        MonitoredService service = mock(MonitoredService.class);

        when(service.changeStatusToUp())
                .thenReturn(ServiceStateChange.DOWN_CONFIRMED);

        ServiceHealthHandler handler = new ServiceHealthHandler(handleServiceDown,
                                                                handleServiceRecovery,
                                                                monitoredServiceRepository,
                                                                observabilityGateway
                                                                );

        handler.handle(service, true);

        verifyNoInteractions(handleServiceRecovery);
        verifyNoInteractions(handleServiceDown);
    }

    @Test
    void shouldHandleDownConfirmed() {
        MonitoredService service = mock(MonitoredService.class);

        when(service.changeStatusToDown())
                .thenReturn(ServiceStateChange.DOWN_CONFIRMED);


        ServiceHealthHandler handler = new ServiceHealthHandler(handleServiceDown, handleServiceRecovery, monitoredServiceRepository, observabilityGateway);

        handler.handle(service, false);

        verify(handleServiceDown).serviceIsDown(service);
        verifyNoInteractions(handleServiceRecovery);
    }

    @Test
    void shouldNotTriggerDownWhenNotConfirmed() {
        MonitoredService service = mock(MonitoredService.class);

        when(service.changeStatusToDown())
                .thenReturn(ServiceStateChange.UP_CONFIRMED);

        ServiceHealthHandler handler = new ServiceHealthHandler(handleServiceDown, handleServiceRecovery, monitoredServiceRepository, observabilityGateway);

        handler.handle(service, false);

        verifyNoInteractions(handleServiceDown);
        verifyNoInteractions(handleServiceRecovery);
    }
}