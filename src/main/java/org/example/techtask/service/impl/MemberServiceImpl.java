package org.example.techtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.response.CreateMemberResponse;
import org.example.techtask.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    @Override
    public CreateMemberResponse createMember(CreateMemberResponse memberDto) {
        return null;
    }

    @Override
    public CreateMemberResponse getMember(UUID memberId) {
        return null;
    }

    @Override
    public CreateMemberResponse updateMember(CreateMemberResponse memberDto) {
        return null;
    }

    @Override
    public void deleteMember(UUID memberId) {

    }
}
