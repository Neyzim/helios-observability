package com.helios.helios.observability.application.mapper;

import com.helios.helios.observability.application.dto.UnavailableMonitoredServiceSummaryDto;
import com.helios.helios.observability.core.domain.service.MonitoredService;

public class ServiceMonitoredUnavailableMapper {

   public UnavailableMonitoredServiceSummaryDto toDto(MonitoredService service){
       return new UnavailableMonitoredServiceSummaryDto(
               service.Name(),
               service.Status().toString(),
               service.Sla().toString()
               );
   }
}
