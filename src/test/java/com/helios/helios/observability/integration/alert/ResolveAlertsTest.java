package com.helios.helios.observability.integration.alert;

import com.helios.helios.observability.application.service.usecases.alert.ResolveAlert;
import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.repository.AlertRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
public class ResolveAlertsTest {

    @Mock
    private AlertRepository alertRepository;

    @InjectMocks
    private ResolveAlert useCase;

    @Test
    void shouldResolveAllOpenAlerts() {
        // given
        Long serviceId = 1L;

        Alert alert1 = mock(Alert.class);
        Alert alert2 = mock(Alert.class);

        when(alertRepository.findOpenAlertsByServiceId(serviceId))
                .thenReturn(List.of(alert1, alert2));

        // when
        useCase.resolve(serviceId);

        // then
        verify(alertRepository).findOpenAlertsByServiceId(serviceId);

        verify(alert1).resolve();
        verify(alert2).resolve();

        verify(alertRepository).save(alert1);
        verify(alertRepository).save(alert2);
    }


}
