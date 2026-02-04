package com.helios.helios.observability.infrastructure.mapper;

import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.infrastructure.dto.ResponseMonitoredSeviceDto;
import com.helios.helios.observability.infrastructure.persistency.entities.MonitoredServiceEntity;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {

    public ResponseMonitoredSeviceDto toDto(MonitoredService service){
        return new ResponseMonitoredSeviceDto(
                service.Id(), service.Name(), service.MonitoredEndpoint(), service.Sla()
        );
    }
}
