package org.example.techtask.dto.member.response;

import java.time.LocalDateTime;

public record GetMemberResponse(String name, LocalDateTime membershipDate) {
}
