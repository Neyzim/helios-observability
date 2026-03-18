package com.helios.helios.observability.core.exception;

public class SeverityCantBeEmpty extends CustomException {
    public SeverityCantBeEmpty() {

        super("Severity Can't be empty", ErrorCode.SERVICE_CANT_BE_EMPTY);
    }
}
