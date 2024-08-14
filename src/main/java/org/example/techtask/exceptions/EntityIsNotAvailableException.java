package org.example.techtask.exceptions;

public class EntityIsNotAvailableException extends RuntimeException {
    private static final String ENTITY_IS_NOT_AVAILABLE = "Entity is not available";

    public EntityIsNotAvailableException(String message) {
        super(message.isEmpty() ? ENTITY_IS_NOT_AVAILABLE : message);
    }

    public EntityIsNotAvailableException() {
        super(ENTITY_IS_NOT_AVAILABLE);
    }

}
