package org.example.techtask.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "member")
public class Member {

    @Id
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "membershipDate")
    private LocalDateTime membershipDate;
}
