package org.example.techtask.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.techtask.validation.annotation.ValidBookTitle;

public class BookTitleValidator implements ConstraintValidator<ValidBookTitle, String> {
    @Override
    public void initialize(ValidBookTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext constraintValidatorContext) {
        if (title == null || title.trim().isEmpty()) {
            return false;
        }

        if (!Character.isUpperCase(title.charAt(0))) {
            return false;
        }

        if (title.length() < 3) {
            return false;
        }

        return true;
    }
}

