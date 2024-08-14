package org.example.techtask.service;

import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.CreateBookResponse;
import org.example.techtask.dto.book.response.GetBookResponse;
import org.example.techtask.dto.book.response.UpdateBookResponse;
import org.example.techtask.dto.member.response.DistinctBorrowedBookResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BookService {

    CreateBookResponse createBook(CreateBookRequest request);

    GetBookResponse getBook(UUID bookId);

    Page<GetBookResponse> getBooksByMember(String name, Integer pageNumber);

    Page<String> getAllDistinctBorrowedBooks(Pageable pageable);

//    Page<DistinctBorrowedBookResponse> getAllDistinctBorrowedBooksAndAmount(Pageable pageable);
    List<DistinctBorrowedBookResponse> getAllDistinctBorrowedBooksAndAmount();
    UpdateBookResponse updateBook(UUID bookId, UpdateBookRequest request);

    void borrowBook(UUID bookId, UUID memberId);

    void returnBook(UUID bookId, UUID memberId);

    void deleteBook(UUID bookId);

}
