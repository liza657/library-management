package org.example.techtask.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.techtask.validation.validator.MemberNameValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = MemberNameValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE,ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidMemberName {
    String message() default "Member name is required";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}