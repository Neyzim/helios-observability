package com.helios.helios.observability.core.exception;

public class AlertAlreadyLinked extends CustomException {

    public AlertAlreadyLinked() {
        super("Alert already linked to an Incident",ErrorCode.ALERT_ALREADY_LINKED);
    }
}
