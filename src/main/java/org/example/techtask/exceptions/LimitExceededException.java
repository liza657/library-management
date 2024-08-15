package org.example.techtask.exceptions;

public class LimitExceededException extends RuntimeException {
    private static final String LIMIT_EXCEED = "Limit exceed";

    public LimitExceededException(String message) {
        super(message.isEmpty() ? LIMIT_EXCEED : message);
    }

}
