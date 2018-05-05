package com.test.list_user.api.exception;

/**
 * Created by Rafael Quiles
 */

public class InsufficientPrivilegesException extends Exception {

    public InsufficientPrivilegesException() {
        super();
    }

    public InsufficientPrivilegesException(String detailMessage) {
        super(detailMessage);
    }

    public InsufficientPrivilegesException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}