package com.yorix.autometer.errors;

public class NoSuchParamsNameException extends ParamException {
    public NoSuchParamsNameException() {
        this("No such param's name.");
    }

    public NoSuchParamsNameException(String message) {
        super(message);
    }

    public NoSuchParamsNameException(String message, Throwable cause) {
        super(message, cause);
    }
}
