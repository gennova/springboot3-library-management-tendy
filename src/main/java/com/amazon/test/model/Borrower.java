package com.amazon.test.model;

import jakarta.persistence.*;

import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "borrower")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@EntityListeners(AuditingEntityListener.class)
public class Borrower {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
}

