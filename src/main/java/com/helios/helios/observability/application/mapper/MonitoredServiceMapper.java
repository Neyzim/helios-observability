package com.helios.helios.observability.application.mapper;

import com.helios.helios.observability.application.dto.MonitoredServiceResponseDto;
import com.helios.helios.observability.core.domain.service.MonitoredService;
import com.helios.helios.observability.core.domain.service.SLAServiceEnum;
import com.helios.helios.observability.core.domain.service.StatusEnum;

public class MonitoredServiceMapper {

    public MonitoredServiceResponseDto toDto(MonitoredService service){
        return new MonitoredServiceResponseDto(
                service.Id(),
                service.Name(),
                service.MonitoredEndpoint()
        );
    }
}
