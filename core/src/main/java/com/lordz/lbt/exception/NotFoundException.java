package com.lordz.lbt.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception of entity not found.
 *
 */
public class NotFoundException extends LBTException {

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.BAD_REQUEST;
    }
}
