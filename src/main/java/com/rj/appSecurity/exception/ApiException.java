package com.rj.appSecurity.exception;

public class ApiException extends RuntimeException {
    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super("on error occurred");
    }


}
