package com.helios.helios.observability.core.exception;

public class ServiceCantBeEmpty extends CustomException {
    public ServiceCantBeEmpty() {
        super("Service cant be empty");
    }
}
