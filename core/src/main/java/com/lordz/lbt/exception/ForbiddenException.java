package com.lordz.lbt.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception caused by accessing forbidden resources.
 *
 * @author johnniang
 */
public class ForbiddenException extends LBTException {

    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.FORBIDDEN;
    }
}
