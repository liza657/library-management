package org.example.techtask.controller;

import lombok.RequiredArgsConstructor;
import org.example.techtask.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
}
