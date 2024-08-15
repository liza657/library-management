package org.example.techtask.service;

import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BookService {

    CreateBookResponse createBook(CreateBookRequest request);

    GetBookResponse getBook(UUID bookId);

    Page<GetBookResponse> getBooksByMember(String name, Integer pageNumber);

    Page<DistinctBorrowedBooksResponse> getAllDistinctBorrowedBooks(Pageable pageable);

    Page<DistinctBorrowedBookAndAmountResponse> getAllDistinctBorrowedBooksAndAmount(Pageable pageable);

    UpdateBookResponse updateBook(UUID bookId, UpdateBookRequest request);

    void borrowBook(UUID bookId, UUID memberId);

    void returnBook(UUID bookId, UUID memberId);

    void deleteBook(UUID bookId);

}
