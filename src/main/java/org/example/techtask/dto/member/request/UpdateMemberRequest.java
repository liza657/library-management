package org.example.techtask.dto.member.request;

import org.example.techtask.validation.annotation.ValidMemberName;

public record UpdateMemberRequest(@ValidMemberName String name) {
}
