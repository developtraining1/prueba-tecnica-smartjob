package com.smart.data.msuser.exception;

public class NotUniqueEmailCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotUniqueEmailCustomException() {
        super();
    }

    public NotUniqueEmailCustomException(String message) {
        super(message);
    }
}
