package org.example.techtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.request.CreateBookRequest;
import org.example.techtask.dto.response.CreateBookResponse;
import org.example.techtask.repository.BookRepository;
import org.example.techtask.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public CreateBookResponse createBook(CreateBookRequest request) {
        return null;
    }

    @Override
    public CreateBookResponse getBook(UUID bookId) {
        return null;
    }

    @Override
    public CreateBookResponse getBooksByMember(UUID memberId) {
        return null;
    }

    @Override
    public Page<CreateBookResponse> getAllDistinctBorrowedBooks() {
        return null;
    }

    @Override
    public Page<CreateBookResponse> getAllDistinctBorrowedBooksAndAmount() {
        return null;
    }

    @Override
    public CreateBookResponse updateBook(CreateBookRequest request) {
        return null;
    }

    @Override
    public void deleteBook(UUID bookId) {

    }
}
