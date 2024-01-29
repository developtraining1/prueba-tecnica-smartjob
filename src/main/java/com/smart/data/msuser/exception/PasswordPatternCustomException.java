package com.smart.data.msuser.exception;

public class PasswordPatternCustomException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PasswordPatternCustomException() {
        super();
    }

    public PasswordPatternCustomException(String message) {
        super(message);
    }
}
