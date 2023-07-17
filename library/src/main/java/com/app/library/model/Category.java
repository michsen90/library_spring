package com.app.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ECategory bookType;

    public Category() {}

    public Category(final ECategory bookType) {
        this.bookType = bookType;
    }

    public Long getId() {
        return id;
    }

    public ECategory getBookType() {
        return bookType;
    }

}
