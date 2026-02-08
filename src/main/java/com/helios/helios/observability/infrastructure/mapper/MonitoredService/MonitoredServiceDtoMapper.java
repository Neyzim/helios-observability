package com.helios.helios.observability.infrastructure.mapper.MonitoredService;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.ResponseMonitoredSeviceDto;
import org.springframework.stereotype.Component;

@Component
public class MonitoredServiceDtoMapper {

    public ResponseMonitoredSeviceDto toDto(MonitoredService service){
        return new ResponseMonitoredSeviceDto(
                service.Id(), service.Name(), service.MonitoredEndpoint(), service.Sla()
        );
    }
}
