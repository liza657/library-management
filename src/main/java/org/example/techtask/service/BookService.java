package org.example.techtask.service;

import org.example.techtask.dto.request.CreateBookRequest;
import org.example.techtask.dto.response.CreateBookResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BookService {

    CreateBookResponse createBook(CreateBookRequest request);
    CreateBookResponse getBook(UUID bookId);
    CreateBookResponse getBooksByMember(UUID memberId);
    Page<CreateBookResponse> getAllDistinctBorrowedBooks();
    Page<CreateBookResponse> getAllDistinctBorrowedBooksAndAmount();
    CreateBookResponse updateBook(CreateBookRequest request);
    void deleteBook(UUID bookId);

}
