package com.smart.data.msuser.exception;

public class NotUniqueUserSecurityCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NotUniqueUserSecurityCustomException() {
        super();
    }

    public NotUniqueUserSecurityCustomException(String message) {
        super(message);
    }

}
