package org.example.techtask.mapper;

import org.example.techtask.dto.member.request.CreateMemberRequest;
import org.example.techtask.dto.member.request.UpdateMemberRequest;
import org.example.techtask.dto.member.response.CreateMemberResponse;
import org.example.techtask.dto.member.response.GetMemberResponse;
import org.example.techtask.dto.member.response.UpdateMemberResponse;
import org.example.techtask.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.time.LocalDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class MemberMapper {

    public Member newToMember(CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.name());
        member.setMembershipDate(LocalDateTime.now());
        return member;
    }

    public Member updateToMember(UpdateMemberRequest request) {
        Member member = new Member();
        member.setName(request.name());
        return member;
    }

    public CreateMemberResponse createMemberToView(Member member) {
        return new CreateMemberResponse(
                member.getName(),
                member.getMembershipDate()
        );
    }

    public GetMemberResponse getMemberToView(Member member) {
        return new GetMemberResponse(
                member.getName(),
                member.getMembershipDate()
        );
    }

    public UpdateMemberResponse updateMemberToView(Member member) {
        return new UpdateMemberResponse(
                member.getName(),
                member.getMembershipDate()
        );
    }


}
