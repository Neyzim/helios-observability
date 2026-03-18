package com.helios.helios.observability.infrastructure.exception;

import com.helios.helios.observability.core.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeHttpMapper {

    public HttpStatus toHttpStatus(ErrorCode errorCode){
        return switch (errorCode){
            case SERVICE_NOT_FOUND -> HttpStatus.NOT_FOUND;
            case ALERT_ALREADY_LINKED -> HttpStatus.CONFLICT;
            case ALERT_ALREADY_SOLVED -> HttpStatus.CONFLICT;
            case SERVICE_CANT_BE_EMPTY -> HttpStatus.UNPROCESSABLE_CONTENT;
            case SEVERITY_CANT_BE_EMPTY -> HttpStatus.UNPROCESSABLE_CONTENT;
            case INCIDENT_ALREADY_SOLVED -> HttpStatus.CONFLICT;
            case ALERT_NOT_FOUND -> HttpStatus.NOT_FOUND;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
