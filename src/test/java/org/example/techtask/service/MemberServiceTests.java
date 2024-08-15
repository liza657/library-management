package org.example.techtask.service;

import org.example.techtask.dto.member.request.CreateMemberRequest;
import org.example.techtask.dto.member.request.UpdateMemberRequest;
import org.example.techtask.dto.member.response.CreateMemberResponse;
import org.example.techtask.dto.member.response.GetMemberResponse;
import org.example.techtask.dto.member.response.UpdateMemberResponse;
import org.example.techtask.exceptions.BookReturnRequiredException;
import org.example.techtask.exceptions.EntityNotExistsException;
import org.example.techtask.mapper.MemberMapper;
import org.example.techtask.model.Book;
import org.example.techtask.model.Member;
import org.example.techtask.repository.MemberRepository;
import org.example.techtask.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTests {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private MemberMapper memberMapper;

    @InjectMocks
    private MemberServiceImpl memberService;


    @Test
    public void MemberService_CreateMember_PositiveCase() {
        Member member = createMember();
        CreateMemberResponse expectedResponse = new CreateMemberResponse(member.getName(), member.getMembershipDate());
        CreateMemberRequest request = new CreateMemberRequest("Liza Dzhuha");
        when(memberMapper.newToMember(request)).thenReturn(member);
        when(memberRepository.save(member)).thenReturn(member);
        when(memberMapper.createMemberToView(member)).thenReturn(expectedResponse);

        CreateMemberResponse result = memberService.createMember(request);

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.name(), result.name(), "The member name should match");

        verify(memberMapper).newToMember(request);
        verify(memberRepository).save(member);
        verify(memberMapper).createMemberToView(member);

    }

    @Test
    public void MemberService_CreateMember_NegativeCase() {
        CreateMemberRequest request = new CreateMemberRequest("Liza Dzhuha");
        Member member = createMember();

        when(memberMapper.newToMember(request)).thenReturn(member);
        when(memberRepository.save(member)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> memberService.createMember(request), "Expected RuntimeException to be thrown");

        verify(memberMapper).newToMember(request);
        verify(memberRepository).save(member);
        verify(memberMapper, never()).createMemberToView(any(Member.class));
    }


    @Test
    public void MemberService_GetMember_PositiveCase() {
        Member member = createMember();

        GetMemberResponse expectedResponse = new GetMemberResponse(member.getName(), member.getMembershipDate());

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));
        when(memberMapper.getMemberToView(member)).thenReturn(expectedResponse);

        GetMemberResponse result = memberService.getMember(member.getId());

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.name(), result.name(), "The member name should match");

        verify(memberRepository).findById(member.getId());
        verify(memberMapper).getMemberToView(member);
    }

    @Test
    public void MemberService_GetMember_NegativeCase() {
        Member member = createMember();

        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> memberService.getMember(member.getId()), "Expected EntityNotExistsException to be thrown");

        verify(memberRepository).findById(member.getId());
        verify(memberMapper, never()).getMemberToView(any(Member.class));
    }

    @Test
    public void MemberService_UpdateMember_PositiveCase() {
        Member member = createMember();
        UpdateMemberRequest request = new UpdateMemberRequest("New Name");

        Member existingMember = new Member();
        existingMember.setId(member.getId());
        existingMember.setName("Old Name");

        UpdateMemberResponse expectedResponse = new UpdateMemberResponse(member.getName(), member.getMembershipDate());

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(existingMember));
        when(memberMapper.updateMemberToView(existingMember)).thenReturn(expectedResponse);

        UpdateMemberResponse result = memberService.updateMember(member.getId(), request);

        assertNotNull(result, "The result should not be null");
        assertEquals(expectedResponse.name(), result.name(), "The member name should match");

        verify(memberRepository).findById(member.getId());
        verify(memberRepository).save(existingMember);
        verify(memberMapper).updateMemberToView(existingMember);
    }

    @Test
    public void MemberService_UpdateMember_NegativeCase() {
        Member member = createMember();
        UpdateMemberRequest request = new UpdateMemberRequest("New Name");

        when(memberRepository.findById(member.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotExistsException.class, () -> memberService.updateMember(member.getId(), request), "Expected EntityNotExistsException to be thrown");

        verify(memberRepository).findById(member.getId());
        verify(memberRepository, never()).save(any(Member.class));
        verify(memberMapper, never()).updateMemberToView(any(Member.class));
    }

    @Test
    public void MemberService_DeleteMember_PositiveCase() {
        Member member = createMember();
        member.setBorrowedBooks(new ArrayList<>());

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        memberService.deleteMember(member.getId());

        verify(memberRepository).findById(member.getId());
        verify(memberRepository).deleteById(member.getId());
    }

    @Test
    public void MemberService_DeleteMember_NegativeCase() {
        Member member = createMember();

        member.setBorrowedBooks(Collections.singletonList(new Book()));

        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        assertThrows(BookReturnRequiredException.class, () -> memberService.deleteMember(member.getId()), "Expected BookReturnRequiredException to be thrown");

        verify(memberRepository).findById(member.getId());
        verify(memberRepository, never()).deleteById(member.getId());
    }

    private Member createMember() {
        return Member.builder()
                .id(UUID.fromString("20533e4d-948d-4c39-b26d-79ae9e5ed710"))
                .name("Liza Dzhuha")
                .membershipDate(LocalDateTime.parse("2024-08-15T14:23:11.503884"))
                .build();
    }

}
