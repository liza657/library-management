package org.example.techtask.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.member.request.CreateMemberRequest;
import org.example.techtask.dto.member.request.UpdateMemberRequest;
import org.example.techtask.dto.member.response.CreateMemberResponse;
import org.example.techtask.dto.member.response.GetMemberResponse;
import org.example.techtask.dto.member.response.UpdateMemberResponse;
import org.example.techtask.exceptions.BookReturnRequiredException;
import org.example.techtask.exceptions.EntityNotExistsException;
import org.example.techtask.mapper.MemberMapper;
import org.example.techtask.model.Member;
import org.example.techtask.repository.MemberRepository;
import org.example.techtask.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    private static final String MEMBER_NOT_FOUND = "Member not found";
    private static final String BOOK_RETURN_REQUIRED = "Book return required";


    @Override
    public CreateMemberResponse createMember(CreateMemberRequest request) {
        Member member = memberMapper.newToMember(request);
        member = memberRepository.save(member);
        return memberMapper.createMemberToView(member);
    }

    @Override
    public GetMemberResponse getMember(UUID memberId) {
        Member member = findMemberById(memberId);
        return memberMapper.getMemberToView(member);
    }

    @Override
    public UpdateMemberResponse updateMember(UUID memberId, UpdateMemberRequest request) {
        Member member = findMemberById(memberId);
        member.setName(request.name());
        memberRepository.save(member);
        return memberMapper.updateMemberToView(member);
    }

    @Override
    public void deleteMember(UUID memberId) {
        Member member = findMemberById(memberId);
        if (member.getBorrowedBooks().isEmpty()) {
            memberRepository.deleteById(memberId);
        } else {
            throw new BookReturnRequiredException(BOOK_RETURN_REQUIRED);
        }
    }

    private Member findMemberById(UUID id) {
        return memberRepository.findById(id).orElseThrow(() -> new EntityNotExistsException(String.format(MEMBER_NOT_FOUND)));

    }
}
