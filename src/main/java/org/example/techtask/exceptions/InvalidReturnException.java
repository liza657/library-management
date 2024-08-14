package org.example.techtask.exceptions;

public class InvalidReturnException extends RuntimeException {
    private static final String INVALID_RETURN = "Invalid return";

    public InvalidReturnException(String message) {
        super(message.isEmpty() ? INVALID_RETURN : message);
    }

    public InvalidReturnException() {
        super(INVALID_RETURN);
    }
}
