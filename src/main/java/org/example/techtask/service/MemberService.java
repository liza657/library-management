package org.example.techtask.service;

import org.example.techtask.dto.response.CreateMemberResponse;

import java.util.UUID;

public interface MemberService {
    CreateMemberResponse createMember(CreateMemberResponse memberDto);
    CreateMemberResponse getMember(UUID memberId);
    CreateMemberResponse updateMember(CreateMemberResponse memberDto);
    void deleteMember(UUID memberId);
}
