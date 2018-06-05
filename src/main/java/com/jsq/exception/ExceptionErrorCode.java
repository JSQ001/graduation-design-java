package com.jsq.exception;

import org.springframework.http.HttpStatus;

/**
 * Created by jsq on 2018/1/10.
 */
public enum  ExceptionErrorCode {
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST),
    OBJECT_NOT_FOUND(HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(HttpStatus.FORBIDDEN),
    SECURITY_VIOLATION(HttpStatus.FORBIDDEN),
    SYSTEM_EXCEPTION(HttpStatus.INTERNAL_SERVER_ERROR),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE);

    HttpStatus httpStatus;

    private ExceptionErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
