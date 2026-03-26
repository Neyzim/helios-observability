package com.helios.helios.observability.integration.monitoredservice;

import com.helios.helios.observability.application.service.usecases.monitoredservice.RegisterMonitoredService;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.repository.MonitoredServiceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class RegisterMonitoredServiceTest {

    @Mock
    private MonitoredServiceRepository repository;

    @InjectMocks
    private RegisterMonitoredService useCase;

    @Test
     void shouldRegisterNewService() {
        // given
        String name = "Service A";
        String url = "http://service.com";
        SLAServiceEnum sla = SLAServiceEnum.URGENT;

        // when
        MonitoredService result = useCase.registerNewMonitoredService(name, url, sla);

        // then
        assertNotNull(result);
        assertEquals(name, result.Name());
        assertEquals(url, result.MonitoredEndpoint());
        assertEquals(sla, result.Sla());

        verify(repository, times(1)).save(result);
    }
}
