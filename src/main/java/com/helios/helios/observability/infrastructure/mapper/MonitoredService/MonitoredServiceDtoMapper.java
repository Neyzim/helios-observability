package com.helios.helios.observability.infrastructure.mapper.MonitoredService;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.dto.monitoredservice.ResponseMonitoredServiceDto;
import org.springframework.stereotype.Component;

@Component
public class MonitoredServiceDtoMapper {

    public ResponseMonitoredServiceDto toDto(MonitoredService service){
        return new ResponseMonitoredServiceDto(
                service.Id(), service.Name(), service.MonitoredEndpoint(), service.Sla()
        );
    }
}
