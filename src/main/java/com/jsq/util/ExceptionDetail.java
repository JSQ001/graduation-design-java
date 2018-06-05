package com.jsq.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.jsq.exception.ExceptionErrorCode;
import com.jsq.exception.ValidationError;

import java.util.List;

/**
 * Created by jsq on 2018/1/10.
 */

@JsonIgnoreProperties(
        ignoreUnknown = true
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"message", "errorCode", "validationErrors", "data"})
public class ExceptionDetail {
    private String message;
    private String errorCode;
    private List<ValidationError> validationErrors;

    public ExceptionDetail() {
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(ExceptionErrorCode errorCode) {
        this.errorCode = errorCode.name();
    }

    public List<ValidationError> getValidationErrors() {
        return this.validationErrors;
    }

    public void setValidationErrors(List<ValidationError> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
