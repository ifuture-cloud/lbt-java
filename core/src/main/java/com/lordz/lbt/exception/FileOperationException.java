package com.lordz.lbt.exception;

/**
 * File operation exception.
 */
public class FileOperationException extends ServiceException {
    public FileOperationException(String message) {
        super(message);
    }

    public FileOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
