package com.helios.helios.observability.infrastructure.exception;

import com.helios.helios.observability.core.exception.ErrorCode;

import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String error; // Http Error
    private String message;
    private String path;
    private String code; //From ErrorCode Enum (Core)

    public ErrorResponse(int status, String error, String message, String path, String code) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public String getCode() {
        return code;
    }
}
