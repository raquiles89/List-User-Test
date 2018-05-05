package com.test.list_user.api.exception;

/**
 * Created by Rafael Quiles
 */

public class ServiceUnavailableException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public ServiceUnavailableException(String message) {
        super(message);
    }

    public ServiceUnavailableException(String message, Throwable e) {
        super(message, e);
    }

}