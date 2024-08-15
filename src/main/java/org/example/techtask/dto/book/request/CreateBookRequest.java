package org.example.techtask.dto.book.request;

import org.example.techtask.validation.annotation.ValidBookAuthorName;
import org.example.techtask.validation.annotation.ValidBookTitle;

public record CreateBookRequest(@ValidBookTitle String title,

                                @ValidBookAuthorName String author) {

}
