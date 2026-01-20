package com.helios.helios.observability.application.service.input;

import com.helios.helios.observability.core.domain.service.MonitoredService;

public interface ServiceAvailabilityInput {

   void serviceIsDown(MonitoredService service);

   void serviceIsUp(MonitoredService service);
}
