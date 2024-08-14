package org.example.techtask.mapper;

import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.book.request.CreateBookRequest;
import org.example.techtask.dto.book.request.UpdateBookRequest;
import org.example.techtask.dto.book.response.CreateBookResponse;
import org.example.techtask.dto.book.response.GetBookResponse;
import org.example.techtask.dto.book.response.UpdateBookResponse;
import org.example.techtask.dto.member.response.DistinctBorrowedBookResponse;
import org.example.techtask.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookMapper {
    public Book newToBook(CreateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        return book;
    }


    public Book updateToBook(UpdateBookRequest request) {
        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        return book;
    }

    public CreateBookResponse createBookToView(Book book) {
        return new CreateBookResponse(
                book.getTitle(),
                book.getAuthor(),
                book.getAmount()
        );
    }

    public GetBookResponse getBookToView(Book book) {
        return new GetBookResponse(
                book.getTitle(),
                book.getAuthor(),
                book.getAmount()
        );
    }

    public UpdateBookResponse updateBookToView(Book book) {
        return new UpdateBookResponse(
                book.getTitle(),
                book.getAuthor(),
                book.getAmount()
        );
    }

}
