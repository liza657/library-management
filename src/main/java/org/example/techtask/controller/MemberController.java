package org.example.techtask.controller;

import lombok.RequiredArgsConstructor;
import org.example.techtask.dto.member.request.CreateMemberRequest;
import org.example.techtask.dto.member.request.UpdateMemberRequest;
import org.example.techtask.dto.member.response.CreateMemberResponse;
import org.example.techtask.dto.member.response.GetMemberResponse;
import org.example.techtask.dto.member.response.UpdateMemberResponse;
import org.example.techtask.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;

    @PostMapping()
    public ResponseEntity<CreateMemberResponse> createMember(@RequestBody CreateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                memberService.createMember(request)
        );
    }

    @GetMapping("{memberId}")
    public ResponseEntity<GetMemberResponse> getMember(@PathVariable("memberId") UUID memberId) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.getMember(memberId));
    }

    @PutMapping("{memberId}")
    public ResponseEntity<UpdateMemberResponse> updateBook(@PathVariable("memberId") UUID memberId, @RequestBody UpdateMemberRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.updateMember(memberId, request));
    }

    @DeleteMapping("{memberId}")
    public void deleteBook(@PathVariable("memberId") UUID memberId) {
        memberService.deleteMember(memberId);
    }

}
