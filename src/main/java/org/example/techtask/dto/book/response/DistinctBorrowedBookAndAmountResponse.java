package org.example.techtask.dto.book.response;

public record DistinctBorrowedBookAndAmountResponse(String title,
                                                    String author,
                                                    Long borrowedAmount) {
}
