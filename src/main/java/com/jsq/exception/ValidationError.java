package com.jsq.exception;

import org.springframework.validation.FieldError;

/**
 * Created by jsq on 2018/1/10.
 */
public class ValidationError {
    private String attributeName;
    private String message;

    public ValidationError() {
    }

    public ValidationError(String attributeName, String message) {
        this.attributeName = attributeName;
        this.message = message;
    }

    public ValidationError(FieldError err) {
        this.attributeName = err.getField();
        this.message = err.getDefaultMessage();
    }

    public String getExternalPropertyName() {
        return this.attributeName;
    }

    public void setExternalPropertyName(String attributeName) {
        this.attributeName = attributeName;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toString() {
        return "ValidationError{attributeName='" + this.attributeName + '\'' + ", message='" + this.message + '\'' + '}';
    }
}
