package com.auction.game.exception;

public class ApiRuntimeException extends RuntimeException {
    public ApiRuntimeException() {
        super();
    }

    public ApiRuntimeException(String message) {
        super(message);
    }

    public ApiRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiRuntimeException(Throwable cause) {
        super(cause);
    }

    protected ApiRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
