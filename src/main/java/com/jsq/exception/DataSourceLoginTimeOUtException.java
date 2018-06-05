package com.jsq.exception;

/**
 * Created by jsq on 2018/1/10.
 */
public class DataSourceLoginTimeOUtException extends RuntimeException{
    private String message = "";

    public DataSourceLoginTimeOUtException(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public String toString() {
        return "ValidationException{validationErrors=" + this.message + '}';
    }
}

