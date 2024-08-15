package org.example.techtask.service;

import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.*;
import org.example.techtask.exceptions.BookBorrowedException;
import org.example.techtask.exceptions.EntityNotExistsException;
import org.example.techtask.exceptions.InvalidReturnException;
import org.example.techtask.exceptions.LimitExceededException;
import org.example.techtask.mapper.BookMapper;
import org.example.techtask.model.Book;
import org.example.techtask.model.Member;
import org.example.techtask.repository.BookRepository;
import org.example.techtask.repository.MemberRepository;
import org.example.techtask.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.*;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.hibernate.validator.internal.util.Contracts.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTests {
    @Mock
    private BookRepository bookRepository;

    @Mock
    MemberRepository memberRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void BookService_CreateBook_PositiveCase() {
        Book existingBook = createBook();
        CreateBookRequest request = new CreateBookRequest("Harry Potter", "Joanne Rowling");

        existingBook.setAmount(2);
        CreateBookResponse expectedResponse = new CreateBookResponse("Harry Potter", "Joanne Rowling", 2);

        when(bookRepository.findByTitleAndAuthor(request.title(), request.author())).thenReturn(Optional.of(existingBook));
        when(bookMapper.createBookToView(existingBook)).thenReturn(expectedResponse);

        CreateBookResponse result = bookService.createBook(request);

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.title(), result.title(), "The title should match");
        assertEquals(expectedResponse.author(), result.author(), "The author should match");
        assertEquals(expectedResponse.amount(), result.amount(), "The amount should match");

        verify(bookRepository).findByTitleAndAuthor(request.title(), request.author());
        verify(bookRepository).save(existingBook);
        verify(bookMapper).createBookToView(existingBook);
    }

    @Test
    public void BookService_CreateBook_NegativeCase() {
        Book newBook = createBook();
        CreateBookRequest request = new CreateBookRequest("Harry Potter", "Joanne Rowling");
        CreateBookResponse expectedResponse = new CreateBookResponse("Harry Potter", "Joanne Rowling", 1);

        when(bookRepository.findByTitleAndAuthor(request.title(), request.author())).thenReturn(Optional.empty());
        when(bookMapper.newToBook(request)).thenReturn(newBook);
        when(bookMapper.createBookToView(newBook)).thenReturn(expectedResponse);

        CreateBookResponse result = bookService.createBook(request);

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.title(), result.title(), "The title should match");
        assertEquals(expectedResponse.author(), result.author(), "The author should match");
        assertEquals(expectedResponse.amount(), result.amount(), "The amount should match");

        verify(bookRepository).findByTitleAndAuthor(request.title(), request.author());
        verify(bookRepository).save(newBook);
        verify(bookMapper).newToBook(request);
        verify(bookMapper).createBookToView(newBook);
    }

    @Test
    public void BookService_UpdateBook_PositiveCase() {
        UpdateBookRequest request = new UpdateBookRequest("New Title", "New Author");
        Book existingBook = createBook();
        UpdateBookResponse expectedResponse = new UpdateBookResponse("New Title", "New Author", 1);

        when(bookRepository.findById(existingBook.getId())).thenReturn(Optional.of(existingBook));
        when(bookMapper.updateBookToView(existingBook)).thenReturn(expectedResponse);

        existingBook.setTitle(request.title());
        existingBook.setAuthor(request.author());
        when(bookRepository.save(existingBook)).thenReturn(existingBook);

        UpdateBookResponse result = bookService.updateBook(existingBook.getId(), request);

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.title(), result.title(), "The title should match");
        assertEquals(expectedResponse.author(), result.author(), "The author should match");

        verify(bookRepository).findById(existingBook.getId());
        verify(bookRepository).save(existingBook);
        verify(bookMapper).updateBookToView(existingBook);
    }


    @Test
    public void BookService_UpdateBook_NegativeCase() {
        Book book = new Book();
        UpdateBookRequest request = new UpdateBookRequest("New Title", "New Author");

        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> bookService.updateBook(book.getId(), request));

        verify(bookRepository).findById(book.getId());
        verify(bookRepository, never()).save(any(Book.class));
        verify(bookMapper, never()).updateBookToView(any(Book.class));
    }

    @Test
    public void BookService_GetBook_PositiveCase() {
        Book book = createBook();
        when(bookRepository.findById(UUID.fromString("1fcf08c4-7ee1-4a05-be02-31f0c503e149"))).thenReturn(Optional.of(book));

        GetBookResponse expectedResponse = new GetBookResponse("Harry Potter", "Joanne Rowling", 1);
        when(bookMapper.getBookToView(book)).thenReturn(expectedResponse);

        GetBookResponse result = bookService.getBook(UUID.fromString("1fcf08c4-7ee1-4a05-be02-31f0c503e149"));

        assertNotNull(result);
        assertEquals("Joanne Rowling", result.author());
        assertEquals("Harry Potter", result.title());
        verify(bookRepository).findById(UUID.fromString("1fcf08c4-7ee1-4a05-be02-31f0c503e149"));
    }


    @Test
    public void BookService_GetBook_NegativeCase() {
        Book book = createBook();
        when(bookRepository.findById(book.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotExistsException.class, () -> bookService.getBook(book.getId()));
    }

    @Test
    public void BookService_DeleteBook_PositiveCase() {
        Book book = createBook();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(memberRepository.findMemberByBorrowedBooksContains(book)).thenReturn(Collections.emptyList());

        bookService.deleteBook(book.getId());

        verify(bookRepository).deleteById(book.getId());

    }

    @Test
    public void BookService_DeleteBook_NegativeCase() {
        Book book = createBook();

        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        when(memberRepository.findMemberByBorrowedBooksContains(book)).thenReturn(List.of(new Member()));

        assertThrows(BookBorrowedException.class, () -> bookService.deleteBook(book.getId()));

    }

    @Test
    public void BookService_GetBooksByMember_PositiveCase() {
        Book book1 = createBook();
        Member member = createMember();
        Book book2 = new Book();
        book2.setId(UUID.randomUUID());
        book2.setTitle("Book 2");
        book2.setAuthor("Author 2");

        int pageNumber = 0;
        List<Book> books = Arrays.asList(book1, book2);
        Page<Book> bookPage = new PageImpl<>(books, PageRequest.of(pageNumber, 20), books.size());

        when(bookRepository.findBorrowedBooksByMemberName(member.getName(), PageRequest.of(pageNumber, 20))).thenReturn(bookPage);

        GetBookResponse response1 = new GetBookResponse("Book 1", "Author 1", 1);
        GetBookResponse response2 = new GetBookResponse("Book 2", "Author 2", 1);

        when(bookMapper.getBookToView(book1)).thenReturn(response1);
        when(bookMapper.getBookToView(book2)).thenReturn(response2);

        Page<GetBookResponse> result = bookService.getBooksByMember(member.getName(), pageNumber);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Book 1", result.getContent().get(0).title());
        assertEquals("Book 2", result.getContent().get(1).title());

        verify(bookRepository).findBorrowedBooksByMemberName(member.getName(), PageRequest.of(pageNumber, 20));
        verify(bookMapper).getBookToView(book1);
        verify(bookMapper).getBookToView(book2);
    }

    @Test
    public void BookService_GetBooksByMember_NegativeCase() {
        Member member = createMember();
        int pageNumber = 0;

        Page<Book> emptyPage = Page.empty(PageRequest.of(pageNumber, 20));

        when(bookRepository.findBorrowedBooksByMemberName(member.getName(), PageRequest.of(pageNumber, 20))).thenReturn(emptyPage);

        Page<GetBookResponse> result = bookService.getBooksByMember(member.getName(), pageNumber);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be empty");

        verify(bookRepository).findBorrowedBooksByMemberName(member.getName(), PageRequest.of(pageNumber, 20));
        verify(bookMapper, never()).getBookToView(any(Book.class));
    }

    @Test
    public void BookService_GetAllDistinctBorrowedBooks_PositiveCase() {
        Pageable pageable = PageRequest.of(0, 10);
        List<String> distinctTitles = Arrays.asList("Harry Potter", "The Hobbit");

        Page<String> titlesPage = new PageImpl<>(distinctTitles, pageable, distinctTitles.size());
        when(bookRepository.findDistinctBorrowedBookNames(pageable)).thenReturn(titlesPage);

        Page<DistinctBorrowedBooksResponse> result = bookService.getAllDistinctBorrowedBooks(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Harry Potter", result.getContent().get(0).title());
        assertEquals("The Hobbit", result.getContent().get(1).title());

        verify(bookRepository).findDistinctBorrowedBookNames(pageable);
    }

    @Test
    public void BookService_GetAllDistinctBorrowedBooks_NegativeCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<String> emptyPage = Page.empty(pageable);

        when(bookRepository.findDistinctBorrowedBookNames(pageable)).thenReturn(emptyPage);

        Page<DistinctBorrowedBooksResponse> result = bookService.getAllDistinctBorrowedBooks(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be empty");

        verify(bookRepository).findDistinctBorrowedBookNames(pageable);
    }

    @Test
    public void BookService_GetAllDistinctBorrowedBooksAndAmount_PositiveCase() {
        Pageable pageable = PageRequest.of(0, 10);
        List<DistinctBorrowedBookAndAmountResponse> distinctBooksAndAmount = Arrays.asList(
                new DistinctBorrowedBookAndAmountResponse("Harry Potter", "gesg", 5L),
                new DistinctBorrowedBookAndAmountResponse("The Hobbit", "dfg", 3L));

        Page<DistinctBorrowedBookAndAmountResponse> responsePage = new PageImpl<>(distinctBooksAndAmount, pageable, distinctBooksAndAmount.size());
        when(bookRepository.findDistinctBorrowedBookNamesAndAmount(pageable)).thenReturn(responsePage);

        Page<DistinctBorrowedBookAndAmountResponse> result = bookService.getAllDistinctBorrowedBooksAndAmount(pageable);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
        assertEquals("Harry Potter", result.getContent().get(0).title());
        assertEquals(5, result.getContent().get(0).borrowedAmount());
        assertEquals("The Hobbit", result.getContent().get(1).title());
        assertEquals(3, result.getContent().get(1).borrowedAmount());

        verify(bookRepository).findDistinctBorrowedBookNamesAndAmount(pageable);
    }

    @Test
    public void BookService_GetAllDistinctBorrowedBooksAndAmount_NegativeCase() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<DistinctBorrowedBookAndAmountResponse> emptyPage = Page.empty(pageable);

        when(bookRepository.findDistinctBorrowedBookNamesAndAmount(pageable)).thenReturn(emptyPage);

        Page<DistinctBorrowedBookAndAmountResponse> result = bookService.getAllDistinctBorrowedBooksAndAmount(pageable);

        assertNotNull(result);
        assertTrue(result.isEmpty(), "The result should be empty");

        verify(bookRepository).findDistinctBorrowedBookNamesAndAmount(pageable);
    }

    @Test
    public void BookService_ReturnBook_PositiveCase() {
        Book book = createBook();
        Member member = createMember();
        member.setBorrowedBooks(new ArrayList<>(Collections.singletonList(book)));

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        bookService.returnBook(book.getId(), member.getId());

        assertEquals(2, book.getAmount());

        verify(bookRepository).save(book);
        verify(memberRepository).save(member);
    }

    @Test
    public void BookService_ReturnBook_NegativeCase() {
        Book book = createBook();
        Member member = createMember();
        member.setBorrowedBooks(Collections.emptyList());

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThrows(InvalidReturnException.class, () -> bookService.returnBook(book.getId(), member.getId()));

        verify(bookRepository, never()).save(any(Book.class));
        verify(memberRepository, never()).save(any(Member.class));
    }


    @Test
    public void BookService_BorrowBook_NegativeCase() {
        Book book = createBook();
        Member member = createMember();
        List<Book> borrowedBooks = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            borrowedBooks.add(new Book());
        }
        member.setBorrowedBooks(borrowedBooks);

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));

        assertThrows(LimitExceededException.class, () -> bookService.borrowBook(book.getId(), member.getId()));

        verify(bookRepository, never()).save(any(Book.class));
        verify(memberRepository, never()).save(any(Member.class));

    }


    private Book createBook() {
        return Book.builder()
                .id(UUID.fromString("1fcf08c4-7ee1-4a05-be02-31f0c503e149"))
                .title("Harry Potter")
                .author("Joanne Rowling")
                .amount(1)
                .build();
    }

    private Member createMember() {
        return Member.builder()
                .id(UUID.fromString("20533e4d-948d-4c39-b26d-79ae9e5ed710"))
                .name("Liza Dzhuha")
                .membershipDate(LocalDateTime.parse("2024-08-15T14:23:11.503884"))
                .build();
    }


}
