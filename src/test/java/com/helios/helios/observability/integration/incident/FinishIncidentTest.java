package com.helios.helios.observability.integration.incident;

import com.helios.helios.observability.application.service.usecases.incident.FinishIncident;
import com.helios.helios.observability.core.domain.incident.Incident;
import com.helios.helios.observability.core.repository.IncidentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class FinishIncidentTest {

    @Mock
    private IncidentRepository incidentRepository;

    @InjectMocks
    private FinishIncident useCase;

    @Test
    void shouldFinishAllOpenIncidents() {
        // given
        Long serviceId = 1L;

        Incident incident1 = mock(Incident.class);
        Incident incident2 = mock(Incident.class);

        when(incidentRepository.findOpenIncidentByServiceId(serviceId))
                .thenReturn(List.of(incident1, incident2));

        // when
        useCase.finish(serviceId);

        // then
        verify(incidentRepository).findOpenIncidentByServiceId(serviceId);

        verify(incident1).finish();
        verify(incident2).finish();

        verify(incidentRepository).save(incident1);
        verify(incidentRepository).save(incident2);
    }
}