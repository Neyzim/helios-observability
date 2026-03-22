package com.helios.helios.observability.unit.monitoredservice.monitoredservice;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.domain.service.ServiceStateChange;
import com.helios.helios.observability.core.domain.service.StatusEnum;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class MonitoredServiceTest{

    @Test
    void shouldStartWithStatusUp(){
        MonitoredService service = MonitoredService.createNew(
                "Test Service",
                "url",
                SLAServiceEnum.DISASTER
        );
        assertEquals(StatusEnum.UP, service.Status());
    }

    @Test
    void shouldIncrementFailureCounterOnConsecutiveDowns(){
        MonitoredService service = MonitoredService.createNew(
                "Cont Test",
                "Count Test Endpoint",
                SLAServiceEnum.ATTENTION
        );
        assertEquals(3, service.Cont());
    }

    @Test
    void shouldResetCounterWhenStatusChanges(){
        MonitoredService service = MonitoredService.createNew(
                "Count reset",
                "Count reset url",
                SLAServiceEnum.URGENT);

        service.changeStatusToDown();
        service.changeStatusToUp();
        service.changeStatusToDown();

        assertEquals(1, service.Cont());
    }

    @Test
    void shouldNotGoDownBeforeFiveFailures(){
        MonitoredService service = MonitoredService.createNew(
                "NOT DOWN BEFORE 5",
                "DOWN BEFORE 5 URL",
                SLAServiceEnum.NORMAL);
        for (int i = 0; i < 4; i++){
            service.changeStatusToDown();
        }
        assertEquals(StatusEnum.UP, service.Status());
    }

    @Test
    void shouldGoDownAfterFiveFailures(){
        MonitoredService service = MonitoredService.createNew(
                "DOWN AFTER 5",
                "DOWN AFTER 5 URL",
                SLAServiceEnum.NORMAL);

        for(int i = 0; i < 5; i++){
            service.changeStatusToDown();
        }

        assertEquals(StatusEnum.DOWN, service.Status());
    }

    @Test
    void shouldNotChangeIfAlreadyDown(){
        MonitoredService service = MonitoredService.createNew(
                "Not change if down",
                "Not change if down url",
                SLAServiceEnum.URGENT);

        for (int i = 0; i < 5; i++) {
            service.changeStatusToDown();
        }

        ServiceStateChange result = service.changeStatusToDown();

        assertEquals(ServiceStateChange.NO_CHANGE, result);
    }

    @Test
    void shouldRecoverToUp() {
        MonitoredService service = MonitoredService.createNew(
                "Should recover to up",
                "Should recover to up url",
                SLAServiceEnum.NORMAL);

        for (int i = 0; i < 5; i++) {
            service.changeStatusToDown();
        }

        service.changeStatusToUp();

        assertEquals(StatusEnum.UP, service.Status());
    }

    @Test
    void shouldResetCounterWhenGoingUp() {
        MonitoredService service = MonitoredService.createNew(
                "Reset counter when up",
                "Reset counter when up url",
                SLAServiceEnum.ATTENTION
        );

        service.changeStatusToDown();
        service.changeStatusToUp();

        assertEquals(0, service.Cont());
    }

    @Test
    void shouldNotChangeIfAlreadyUp() {
        MonitoredService service = MonitoredService.createNew(
                "Should not change if already up",
                "Should not change if already up url",
                SLAServiceEnum.URGENT);

        ServiceStateChange result = service.changeStatusToUp();

        assertEquals(ServiceStateChange.NO_CHANGE, result);
    }

    @Test
    void shouldHandleFullFailureAndRecoveryFlow() {
        MonitoredService service = MonitoredService.createNew(
                "Handle failure and recover",
                "Handle failure and recover",
                SLAServiceEnum.ATTENTION);

        // 4 falhas → ainda UP
        for (int i = 0; i < 4; i++) {
            service.changeStatusToDown();
        }
        assertEquals(StatusEnum.UP, service.Status());

        // 5ª falha → DOWN
        service.changeStatusToDown();
        assertEquals(StatusEnum.DOWN, service.Status());

        // recuperação
        service.changeStatusToUp();
        assertEquals(StatusEnum.UP, service.Status());
    }
}
