package org.example.techtask.service;

import org.example.techtask.dto.member.request.CreateMemberRequest;
import org.example.techtask.dto.member.request.UpdateMemberRequest;
import org.example.techtask.dto.member.response.CreateMemberResponse;
import org.example.techtask.dto.member.response.GetMemberResponse;
import org.example.techtask.dto.member.response.UpdateMemberResponse;

import java.util.UUID;

public interface MemberService {
    CreateMemberResponse createMember(CreateMemberRequest request);

    GetMemberResponse getMember(UUID memberId);

    UpdateMemberResponse updateMember(UUID memberId, UpdateMemberRequest request);

    void deleteMember(UUID memberId);
}
