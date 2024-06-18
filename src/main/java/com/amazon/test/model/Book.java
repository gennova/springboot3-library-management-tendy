package com.amazon.test.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "book")
@Setter
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique=true)
    private long id;
    @Column(name = "isbn", nullable = false)
    private String isbn;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "author", nullable = false)
    private String author;

    public Book(String isbn, String title, String author) {
        this.isbn=isbn;
        this.title=title;
        this.author=author;
    }
}
