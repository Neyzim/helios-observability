package com.helios.helios.observability.core.domain.service;

import com.helios.helios.observability.core.exception.ServiceAlreadyDown;
import com.helios.helios.observability.core.exception.ServiceAlreadyUp;

public class MonitoredService {
    /*
    -INVARIANTS:
        * A service always starts (is created) with status UP.
        * If the Status is already DOWN, it cannot be marked as DOWN again.
        * If the Status is already UP, it cannot be marked as UP again.
     */
    private Long id;
    private String name;
    private String monitoredEndpoint;
    private StatusEnum status = StatusEnum.UP;
    private SLAServiceEnum sla;
    private Integer cont = 0;
    private StatusEnum lastEvent;


    public static MonitoredService createNew(String name, String monitoredEndpoint, SLAServiceEnum sla) {
        MonitoredService service = new MonitoredService();
        service.name = name;
        service.monitoredEndpoint = monitoredEndpoint;
        service.sla = sla;
        return service;
    }

    public static MonitoredService rehydrate(Long id, String name, String monitoredEndpoint, StatusEnum status, SLAServiceEnum sla){
        MonitoredService service = new MonitoredService();
        service.id = id;
        service.name = name;
        service.monitoredEndpoint = monitoredEndpoint;
        service.status = status;
        service.sla = sla;
        return service;
    }



    public ServiceStateChange changeStatusToDown() {
        if (lastEvent == StatusEnum.DOWN) {
            cont = cont + 1;
        }else{
            cont = 1;
        }
        lastEvent = StatusEnum.DOWN;
        if (cont < 5) {
            return ServiceStateChange.NO_CHANGE;
        }
        if(this.status == StatusEnum.DOWN){
            return ServiceStateChange.NO_CHANGE;
        }
        this.status = StatusEnum.DOWN;
        return ServiceStateChange.DOWN_CONFIRMED;
    }

    public ServiceStateChange changeStatusToUp(){

        if (lastEvent == StatusEnum.UP) {
            cont = cont + 1;
        }else{
            cont = 1;
        }
        lastEvent = StatusEnum.UP;
        if (cont < 5) {
            return ServiceStateChange.NO_CHANGE;
        }
        if(this.status == StatusEnum.UP){
            return ServiceStateChange.NO_CHANGE;
        }
        this.status = StatusEnum.UP;
        return ServiceStateChange.UP_CONFIRMED;
    }

    public Long Id() {
        return id;
    }

    public String Name() {
        return name;
    }

    public String MonitoredEndpoint() {
        return monitoredEndpoint;
    }

    public StatusEnum Status() {
        return status;
    }

    public SLAServiceEnum Sla() {
        return sla;
    }
}
