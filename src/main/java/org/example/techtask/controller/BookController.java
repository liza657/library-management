package org.example.techtask.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.CreateBookResponse;
import org.example.techtask.dto.book.response.GetBookResponse;
import org.example.techtask.dto.book.response.UpdateBookResponse;
import org.example.techtask.dto.member.response.DistinctBorrowedBookResponse;
import org.example.techtask.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @PostMapping()
    public ResponseEntity<CreateBookResponse> createBook(@Valid @RequestBody CreateBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                bookService.createBook(request)
        );
    }

    @GetMapping("{bookId}")
    public ResponseEntity<GetBookResponse> getBook(@PathVariable("bookId") UUID bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getBook(bookId));
    }

    @PutMapping("{bookId}")
    public ResponseEntity<UpdateBookResponse> updateBook(@PathVariable("bookId") UUID bookId,@Valid  @RequestBody UpdateBookRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(bookId, request));
    }

    @DeleteMapping("{bookId}")
    public void deleteBook(@PathVariable("bookId") UUID bookId) {
        bookService.deleteBook(bookId);
    }


    @PostMapping("borrow/{bookId}/{memberId}")
    public void borrowBook(@PathVariable("bookId") UUID bookId,
                           @PathVariable("memberId") UUID memberId) {
        bookService.borrowBook(bookId, memberId);
    }

    @PostMapping("return/{bookId}/{memberId}")
    public void returnBook(@PathVariable("bookId") UUID bookId,
                           @PathVariable("memberId") UUID memberId) {
        bookService.returnBook(bookId, memberId);
    }

    @GetMapping("byMember")
    public ResponseEntity<Page<GetBookResponse>> getByMember(@RequestParam String memberName,
                                                             @RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(
                bookService.getBooksByMember(memberName, pageNumber)
        );
    }

    @GetMapping("DistinctBorrowedBooks")
    public ResponseEntity<Page<String>> getAllDistinctBorrowedBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllDistinctBorrowedBooks(pageable));
    }

//    @GetMapping("DistinctBorrowedBookNamesAndAmount")
//    public ResponseEntity<Page<GetBookResponse>> getAllDistinctBorrowedBooksAndAmount(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllDistinctBorrowedBooksAndAmount(pageable));
//    }
@GetMapping("DistinctBorrowedBookNamesAndAmount")
public ResponseEntity<List<DistinctBorrowedBookResponse>> getAllDistinctBorrowedBooksAndAmount(
       ) {
    return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllDistinctBorrowedBooksAndAmount());
}
}
