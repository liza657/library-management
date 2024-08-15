package org.example.techtask.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.techtask.validation.validator.BookAuthorNameValidator;

import java.lang.annotation.*;

@Constraint(validatedBy = BookAuthorNameValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidBookAuthorName {
    String message() default "Author name must contain two capital words with a space between (e.g., Paulo Coelho)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
