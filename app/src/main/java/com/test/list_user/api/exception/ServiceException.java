package com.test.list_user.api.exception;

/**
 * Created by Rafael Quiles
 */

public class ServiceException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = -6831637041721882627L;

    /**
     *
     */
    public ServiceException() {
    }

    /**
     * @param detailMessage text message
     */
    public ServiceException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * @param throwable error
     */
    public ServiceException(Throwable throwable) {
        super(throwable);
    }

    /**
     * @param detailMessage text message
     * @param throwable     error
     */
    public ServiceException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

}

