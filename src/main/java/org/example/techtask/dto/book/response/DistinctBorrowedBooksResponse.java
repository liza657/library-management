package org.example.techtask.dto.book.response;

import lombok.Builder;

@Builder
public record DistinctBorrowedBooksResponse(String title) {
}
