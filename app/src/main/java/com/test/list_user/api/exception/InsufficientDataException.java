package com.test.list_user.api.exception;

/**
 * Created by Rafael Quiles
 */

public class InsufficientDataException extends Exception {
    public InsufficientDataException() {
        super();
    }

    public InsufficientDataException(final String exceptionMessage) {
        super(exceptionMessage);
    }

    public InsufficientDataException(final String message, final Throwable throwable) {
        super(message, throwable);
    }
}
