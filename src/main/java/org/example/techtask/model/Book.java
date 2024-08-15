package org.example.techtask.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    @UuidGenerator
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "author")
    @NotBlank(message = "Author is required")
    private String author;

    @Column(name = "amount")
    private Integer amount;
}
