package com.helios.helios.observability.core.exception;

public class AlertAlreadySolved extends CustomException {

    public AlertAlreadySolved() {
        super("It is not possible to solve an alert that is already solved");
    }
}
