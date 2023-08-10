package com.glauber.tdd.exceptions;

public class NoClientException extends RuntimeException {
    public NoClientException() {
        super ("Cliente não pode ser nulo!");
    }

    public NoClientException(String message) {
        super(message);
    }
}
