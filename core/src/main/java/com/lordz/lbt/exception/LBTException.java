package com.lordz.lbt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * Base exception of the LBT(lanbitou烂笔头).
 * @author ：zzz
 * @date ：Created in 7/11/19 7:15 PM
 */
public abstract class LBTException extends RuntimeException {

    /**
     * Error errorData.
     */
    private Object errorData;

    public LBTException(String message) {
        super(message);
    }

    public LBTException(String message, Throwable cause) {
        super(message, cause);
    }

    @NonNull
    public abstract HttpStatus getStatus();

    @Nullable
    public Object getErrorData() {
        return errorData;
    }

    /**
     * Sets error errorData.
     *
     * @param errorData error data
     * @return current exception.
     */
    @NonNull
    public LBTException setErrorData(@Nullable Object errorData) {
        this.errorData = errorData;
        return this;
    }
}
