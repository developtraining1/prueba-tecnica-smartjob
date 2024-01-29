package com.smart.data.msuser.exception;

public class ResourceNotFoundCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundCustomException() {
        super();
    }

    public ResourceNotFoundCustomException(String message) {
        super(message);
    }

}
