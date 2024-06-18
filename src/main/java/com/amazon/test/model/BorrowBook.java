package com.amazon.test.model;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Date;

@Entity
@Table(name = "borrowbook")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class BorrowBook {
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
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "dateborrow", nullable = false)
    private String dateborrow;
}
