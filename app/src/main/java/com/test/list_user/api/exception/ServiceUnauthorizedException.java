package com.test.list_user.api.exception;

/**
 * Created by Rafael Quiles
 */

public class ServiceUnauthorizedException extends ServiceException {

    private static final long serialVersionUID = 1L;

    public ServiceUnauthorizedException() {
    }

    public ServiceUnauthorizedException(String message) {
        super(message);
    }

    public ServiceUnauthorizedException(String message, Throwable e) {
        super(message, e);
    }
}
