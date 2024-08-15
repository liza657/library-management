package org.example.techtask.dto.book.request;

import org.example.techtask.validation.annotation.ValidBookAuthorName;
import org.example.techtask.validation.annotation.ValidBookTitle;

public record UpdateBookRequest(@ValidBookTitle String title,

                                @ValidBookAuthorName String author) {

}
