package com.iyzico.challenge.core.exception;

public class SeatPurchasedException extends RuntimeException{

    private final String message;

    public SeatPurchasedException(){
        this("seat.purchased.exception");
    }
    public SeatPurchasedException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
