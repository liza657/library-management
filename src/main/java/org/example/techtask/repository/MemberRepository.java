package org.example.techtask.repository;

import org.example.techtask.model.Book;
import org.springframework.data.domain.Page;
import org.example.techtask.model.Member;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
    List<Member> findMemberByBorrowedBooksContains(Book book);

}
