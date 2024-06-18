package com.amazon.test.model;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookStock extends Book{
    @Column(name = "stock", nullable = false)
    private int stock;
    @Column(name = "borrowed", nullable = false)
    private int borrowed;
    @Column(name = "available", nullable = false)
    private int available;
}
