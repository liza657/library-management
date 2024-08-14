package org.example.techtask.dto.member.response;

import java.time.LocalDateTime;

public record CreateMemberResponse(String name, LocalDateTime membershipDate) {
}
