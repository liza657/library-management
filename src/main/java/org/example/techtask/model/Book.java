package org.example.techtask.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "book")
public class Book {

    @Id
    private UUID id;
    private String title;
    private String author;
    private String amount;
}
