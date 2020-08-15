package ru.otus.dao;

public class ResourceAccessException extends RuntimeException {
    public ResourceAccessException() {
        super();
    }

    public ResourceAccessException(String message) {
        super(message);
    }

    public ResourceAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceAccessException(Throwable cause) {
        super(cause);
    }

    protected ResourceAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
