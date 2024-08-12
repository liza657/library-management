package org.example.techtask.repository;

import org.example.techtask.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
}
