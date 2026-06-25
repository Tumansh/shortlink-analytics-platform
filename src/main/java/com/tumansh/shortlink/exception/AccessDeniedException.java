package com.tumansh.shortlink.exception;

public class AccessDeniedException
        extends RuntimeException {

    public AccessDeniedException(
            String message) {

        super(message);
    }
}
