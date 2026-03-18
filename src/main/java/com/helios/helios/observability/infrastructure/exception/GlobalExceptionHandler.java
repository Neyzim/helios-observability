package com.helios.helios.observability.infrastructure.exception;

import com.helios.helios.observability.core.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorCodeHttpMapper mapper;

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
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}
