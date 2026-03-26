package com.helios.helios.observability.unit.alert;

import com.helios.helios.observability.core.domain.alert.Alert;
import com.helios.helios.observability.core.domain.alert.AlertType;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.exception.AlertAlreadyLinked;
import com.helios.helios.observability.core.exception.AlertAlreadySolved;
import com.helios.helios.observability.core.exception.ServiceCantBeEmpty;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AlertTest {

    private MonitoredService createService() {
        return MonitoredService.createNew(
                "Test Service",
                "http://test.com",
                SLAServiceEnum.URGENT
        );
    }

    @Test
    void shouldCreateAlertSuccessfully() {
        MonitoredService service = createService();

        Alert alert = Alert.createNew(service, AlertType.DOWN);

        assertNotNull(alert);
        assertEquals(service, alert.Service());
        assertEquals(AlertType.DOWN, alert.Type());
        assertNotNull(alert.CreatedAt());
        assertNull(alert.SolvedAt());
    }

    @Test
    void shouldThrowExceptionWhenServiceIsNull() {
        assertThrows(ServiceCantBeEmpty.class, () -> {
            Alert.createNew(null, AlertType.DOWN);
        });
    }

    @Test
    void shouldResolveAlert() {
        Alert alert = Alert.createNew(createService(), AlertType.DOWN);

        alert.resolve();

        assertNotNull(alert.SolvedAt());
    }

    @Test
    void shouldNotResolveAlertTwice() {
        Alert alert = Alert.createNew(createService(), AlertType.DOWN);

        alert.resolve();

        assertThrows(AlertAlreadySolved.class, alert::resolve);
    }

    @Test
    void shouldAssignIncident() {
        Alert alert = Alert.createNew(createService(), AlertType.DOWN);

        alert.assignIncident(1L);

        assertEquals(1L, alert.IncidentId());
    }

    @Test
    void shouldNotAssignIncidentTwice() {
        Alert alert = Alert.createNew(createService(), AlertType.DOWN);

        alert.assignIncident(1L);

        assertThrows(AlertAlreadyLinked.class, () -> {
            alert.assignIncident(2L);
        });
    }

}
