package com.jsq.exception;

public class ObjectNotFoundException extends RuntimeException {
    public ObjectNotFoundException(Class targetObject, Long targetObjectID) {
        super(targetObject.getSimpleName() + "[id=" + targetObjectID + "] not found");
    }

    public ObjectNotFoundException(Class targetObject, String message) {
        super(targetObject.getSimpleName() + "[" + message + "] not found");
    }
}