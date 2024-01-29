package com.smart.data.msuser.exception;

public class NotAuthorizedCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotAuthorizedCustomException() {
        super();
    }

    public NotAuthorizedCustomException(String message) {
        super(message);
    }
}
