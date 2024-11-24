package com.iyzico.challenge.core.exception;

public class EntityNotFoundException extends RuntimeException{

    private final String message; //This will go to translation.

    private final String causeMessage; //Here I will write the name of the entity that has not been found.

    public EntityNotFoundException(String message, String causeMessage) {
        super(message);
        this.message = message;
        this.causeMessage = causeMessage;
    }

    public EntityNotFoundException(String causeMessage) {
        this("errors.entity_not_found",causeMessage);
    }

    public EntityNotFoundException(){
        this("errors.entity_not_found",null);
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCauseMessage() {
        return causeMessage;
    }
}
