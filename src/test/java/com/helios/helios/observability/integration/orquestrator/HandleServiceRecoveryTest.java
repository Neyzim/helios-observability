package com.helios.helios.observability.integration.orquestrator;


import com.helios.helios.observability.application.service.usecases.orquestrator.HandleServiceRecovery;
import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class HandleServiceRecoveryTest {

    @Mock
    private FinishIncident finishIncident;

    @Mock
    private ResolveAlert resolveAlert;

    @InjectMocks
    private HandleServiceRecovery useCase;

    @Test
    void shouldHandleRecoveryWhenUpConfirmed() {
        // given
        Long serviceId = 1L;
        MonitoredService service = mock(MonitoredService.class);


        when(service.Id()).thenReturn(serviceId);

        // when
        useCase.resolve(service);

        // then
        verify(resolveAlert).resolve(serviceId);
        verify(finishIncident).finish(serviceId);
    }
}