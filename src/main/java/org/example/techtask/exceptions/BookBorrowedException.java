package org.example.techtask.exceptions;

public class BookBorrowedException extends RuntimeException{
    private static final String BOOK_BORROWED = "Book return required";

    public BookBorrowedException(String message) {
        super(message.isEmpty() ? BOOK_BORROWED : message);
    }

    public BookBorrowedException() {
        super(BOOK_BORROWED);
    }

}
