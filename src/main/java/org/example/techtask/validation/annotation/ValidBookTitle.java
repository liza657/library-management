package org.example.techtask.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.techtask.validation.validator.BookTitleValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = BookTitleValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidBookTitle {
    String message() default "Book title is required, should start with a capital letter, min length - 3 symbols";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
