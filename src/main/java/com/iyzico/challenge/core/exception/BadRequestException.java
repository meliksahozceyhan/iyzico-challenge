package com.iyzico.challenge.core.exception;

public class BadRequestException extends RuntimeException {
    private final String message;

    private final String field;

    public BadRequestException(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public BadRequestException(String message){
        this(message,null);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }
}
