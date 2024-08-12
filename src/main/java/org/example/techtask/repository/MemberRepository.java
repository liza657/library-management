package org.example.techtask.repository;

import org.example.techtask.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberRepository extends JpaRepository<Member, UUID> {
}
