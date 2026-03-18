package com.helios.helios.observability.core.exception;

public class ServiceNotFound extends CustomException {

    public ServiceNotFound() {

        super("Service not found", ErrorCode.SERVICE_NOT_FOUND);
    }
}
