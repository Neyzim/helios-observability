package com.helios.helios.observability.integration.alert;

import com.helios.helios.observability.application.service.usecases.alert.CreateAlert;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class CreateAlertTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private CreateAlert useCase;

    private MonitoredService createService() {
        return MonitoredService.createNew(
                "Service",
                "http://test.com",
                SLAServiceEnum.URGENT
        );
    }

    @Test
    void shouldCreateAndSaveAlert() {
        // given
        MonitoredService service = createService();

        AlertType type = AlertType.DOWN;

        // when
        Alert result = useCase.createAlert(service, type);

        // then
        assertNotNull(result);
        assertEquals(service, result.Service());
        assertEquals(type, result.Type());
        assertNotNull(result.CreatedAt());
        assertNull(result.SolvedAt());


        verify(alertRepository, times(1)).save(result);
    }
}