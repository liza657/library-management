package org.example.techtask.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.techtask.validation.annotation.ValidMemberName;

public class MemberNameValidator implements ConstraintValidator<ValidMemberName, String> {

    @Override
    public void initialize(ValidMemberName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        return name != null && !name.trim().isEmpty();
    }
}
