package com.auction.game.exception;

public class NotFoundSuchEntityException extends RuntimeException {
    public NotFoundSuchEntityException() {
        super();
    }

    public NotFoundSuchEntityException(String message) {
        super(message);
    }

    public NotFoundSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotFoundSuchEntityException(Throwable cause) {
        super(cause);
    }

    protected NotFoundSuchEntityException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
