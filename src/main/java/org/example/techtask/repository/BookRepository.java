package org.example.techtask.repository;

import org.example.techtask.dto.book.response.DistinctBorrowedBookAndAmountResponse;
import org.example.techtask.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitleAndAuthor(String title, String author);

    @Query("SELECT b FROM Member m JOIN m.borrowedBooks b WHERE m.name = :memberName")
    Page<Book> findBorrowedBooksByMemberName(String memberName, Pageable pageable);

    @Query("SELECT DISTINCT b.title FROM Member m JOIN m.borrowedBooks b")
    Page<String> findDistinctBorrowedBookNames(Pageable pageable);

    @Query("SELECT new org.example.techtask.dto.book.response.DistinctBorrowedBookAndAmountResponse(b.title,b.author, COUNT(b)) " +
            "FROM Member m JOIN m.borrowedBooks b " +
            "GROUP BY b.title")
    Page<DistinctBorrowedBookAndAmountResponse> findDistinctBorrowedBookNamesAndAmount(Pageable pageable);

}
