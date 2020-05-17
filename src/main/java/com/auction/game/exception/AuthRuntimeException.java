package com.auction.game.exception;

public class AuthRuntimeException extends RuntimeException {
    public AuthRuntimeException() {
        super();
    }

    public AuthRuntimeException(String message) {
        super(message);
    }

    public AuthRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthRuntimeException(Throwable cause) {
        super(cause);
    }

    protected AuthRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
