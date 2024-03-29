package com.lordz.lbt.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by service.
 */
public class ServiceException extends LBTException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
