package com.yorix.autometer.errors;

public class ParamException extends RuntimeException {
    public ParamException(String message) {
        super(message);
    }

    public ParamException(String message, Throwable cause) {
        super(message, cause);
    }
}
