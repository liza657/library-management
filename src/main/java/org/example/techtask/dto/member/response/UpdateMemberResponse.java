package org.example.techtask.dto.member.response;

import java.time.LocalDateTime;

public record UpdateMemberResponse(String name, LocalDateTime membershipDate) {
}
