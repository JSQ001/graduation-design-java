package com.jsq.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jsq on 2018/1/10.
 */
public class ValidationException extends RuntimeException {
    private List<ValidationError> validationErrors = new ArrayList();

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public ValidationException(ValidationError validationError) {
        this.validationErrors.add(validationError);
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    public String toString() {
        return "ValidationException{validationErrors=" + this.validationErrors + '}';
    }
}
