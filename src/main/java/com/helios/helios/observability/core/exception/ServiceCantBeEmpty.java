package com.helios.helios.observability.core.exception;

public class ServiceCantBeEmpty extends CustomException {
    public ServiceCantBeEmpty() {
        super("Service cant be empty", ErrorCode.SERVICE_CANT_BE_EMPTY);
    }
}
