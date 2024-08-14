package org.example.techtask.exceptions;

public class BookReturnRequiredException extends RuntimeException{
    private static final String BOOK_RETURN_REQUIRED = "Book return required";

    public BookReturnRequiredException(String message) {
        super(message.isEmpty() ? BOOK_RETURN_REQUIRED : message);
    }

    public BookReturnRequiredException() {
        super(BOOK_RETURN_REQUIRED);
    }

}
