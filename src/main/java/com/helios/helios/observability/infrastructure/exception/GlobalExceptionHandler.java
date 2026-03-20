package com.helios.helios.observability.infrastructure.exception;

import com.helios.helios.observability.core.exception.CustomException;
import com.helios.helios.observability.infrastructure.controllers.MonitoredServiceController;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorCodeHttpMapper mapper;
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    public GlobalExceptionHandler(ErrorCodeHttpMapper mapper) {
        this.mapper = mapper;
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(
            CustomException customException,
            HttpServletRequest request
    ){
        HttpStatus status = mapper.toHttpStatus(customException.getErrorCode());
        ErrorResponse response = new ErrorResponse(
                status.value(),
                status.name(),
                customException.getMessage(),
                request.getRequestURI(),
                customException.getErrorCode().name()
        );
        log.error("Request {} {} failed with errorCode={} message={}",
                request.getMethod(),
                request.getRequestURI(),
                customException.getErrorCode(),
                customException.getMessage(),
                customException
        );
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception exception,
            HttpServletRequest request
    ){
        ErrorResponse response = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                "Internal Server Error",
                request.getRequestURI(),
                "INTERNAL ERROR"
        );
        log.error("Unexpected error on {} {}",
                request.getMethod(),
                request.getRequestURI(),
                exception
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
