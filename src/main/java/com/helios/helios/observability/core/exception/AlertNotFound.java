package com.helios.helios.observability.core.exception;

public class AlertNotFound extends CustomException {
    public AlertNotFound() {

        super("Alert not found", ErrorCode.ALERT_NOT_FOUND);
    }
}
