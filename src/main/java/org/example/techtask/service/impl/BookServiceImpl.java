package org.example.techtask.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.CreateBookResponse;
import org.example.techtask.dto.book.response.GetBookResponse;
import org.example.techtask.dto.book.response.UpdateBookResponse;
import org.example.techtask.dto.member.response.DistinctBorrowedBookResponse;
import org.example.techtask.exceptions.*;
import org.example.techtask.mapper.BookMapper;
import org.example.techtask.model.Book;
import org.example.techtask.model.Member;
import org.example.techtask.repository.BookRepository;
import org.example.techtask.repository.MemberRepository;
import org.example.techtask.service.BookService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;
    private final BookMapper bookMapper;
    private static final String BOOK_NOT_FOUND = "Book not found";
    private static final String MEMBER_NOT_FOUND = "Member not found";
    private final static String BOOK_IS_NOT_AVAILABLE = "Book is not available";
    private final static String LIMIT_EXCEED = "Borrowing limit exceeded.";
    private static final String INVALID_RETURN = "You cannot return a book you haven't borrowed";
    private static final String BOOK_BORROWED = "Cannot delete the book as it is currently borrowed by one or more members.";


    @Value("${library.borrow.limit}")
    private int borrowLimit;


    @Override
    public CreateBookResponse createBook(CreateBookRequest request) {
        Optional<Book> existingBook = bookRepository.findByTitleAndAuthor(request.title(), request.author());

        Book book;
        if (existingBook.isPresent()) {
            book = existingBook.get();
            book.setAmount(book.getAmount() + 1);
        } else {
            book = bookMapper.newToBook(request);
            book.setAmount(1);
        }
        bookRepository.save(book);
        return bookMapper.createBookToView(book);
    }

    @Override
    public GetBookResponse getBook(UUID bookId) {
        Book book = findBookById(bookId);
        return bookMapper.getBookToView(book);
    }

    @Override
    public UpdateBookResponse updateBook(UUID bookId, UpdateBookRequest request) {
        Book book = findBookById(bookId);
        book.setTitle(request.title());
        bookRepository.save(book);
        return bookMapper.updateBookToView(book);
    }

    @Override
    public void borrowBook(UUID bookId, UUID memberId) {
        Member member = getCurrentMember(memberId);
        Book book = findBookById(bookId);

        if (book.getAmount() <= 0) {
            throw new EntityIsNotAvailableException(BOOK_IS_NOT_AVAILABLE);
        }

        if (member.getBorrowedBooks().size() >= borrowLimit) {
            throw new LimitExceededException(LIMIT_EXCEED);
        }

        member.getBorrowedBooks().add(book);
        book.setAmount(book.getAmount() - 1);
        bookRepository.save(book);
        memberRepository.save(member);

    }

    @Override
    public void returnBook(UUID bookId, UUID memberId) {
        Member member = getCurrentMember(memberId);
        Book book = findBookById(bookId);
        if (member.getBorrowedBooks().contains(book)) {
            member.getBorrowedBooks().remove(book);
            book.setAmount(book.getAmount() + 1);
            bookRepository.save(book);
            memberRepository.save(member);
        } else {
            throw new InvalidReturnException(INVALID_RETURN);
        }
    }

    @Override
    public void deleteBook(UUID bookId) {
        Book book = findBookById(bookId);

        List<Member> membersWithBook = memberRepository.findMemberByBorrowedBooksContains(book);

        if (membersWithBook.isEmpty()) {
            bookRepository.deleteById(bookId);
        } else {
            throw new BookBorrowedException(BOOK_BORROWED);
        }
    }

    @Override
    public Page<GetBookResponse> getBooksByMember(String name, Integer pageNumber) {
        Page<Book> books = bookRepository.findBorrowedBooksByMemberName(name, PageRequest.of(pageNumber, 20));
        return books.map(bookMapper::getBookToView);
    }

    @Override
    public Page<String> getAllDistinctBorrowedBooks(Pageable pageable) {
        return bookRepository.findDistinctBorrowedBookNames(pageable);
    }

//    @Override
//    public Page<DistinctBorrowedBookResponse> getAllDistinctBorrowedBooksAndAmount(Pageable pageable) {
//        Page<DistinctBorrowedBookResponse> books = bookRepository.findDistinctBorrowedBookNamesAndAmount(pageable);
//        return books.map(bookMapper::borrowedBookToView);
//    }
    @Override
public List<DistinctBorrowedBookResponse> getAllDistinctBorrowedBooksAndAmount() {
    return bookRepository.findDistinctBorrowedBookNamesAndAmount();
}

    private Book findBookById(UUID id) {
        return bookRepository.findById(id).orElseThrow(() -> new EntityNotExistsException(String.format(BOOK_NOT_FOUND)));

    }

    private Member getCurrentMember(UUID memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new EntityNotExistsException(String.format(MEMBER_NOT_FOUND)));
    }

}
