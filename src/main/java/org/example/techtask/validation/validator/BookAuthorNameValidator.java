package org.example.techtask.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.techtask.validation.annotation.ValidBookAuthorName;

public class BookAuthorNameValidator implements ConstraintValidator<ValidBookAuthorName, String> {
    @Override
    public void initialize(ValidBookAuthorName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String author, ConstraintValidatorContext context) {
        if (author == null || author.trim().isEmpty()) {
            return false;
        }

        String[] parts = author.trim().split("\\s+");

        if (parts.length != 2) {
            return false;
        }

        return isCapitalized(parts[0]) && isCapitalized(parts[1]);
    }

    private boolean isCapitalized(String word) {
        return Character.isUpperCase(word.charAt(0)) && word.substring(1).chars().allMatch(Character::isLowerCase);
    }
}

