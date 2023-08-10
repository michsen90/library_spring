package com.app.library.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "book_item")
public class BookItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean available;

    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToOne(mappedBy = "bookItem")
    private RentBook rentBook;

    public BookItem() {
        this.available = true;
        this.createdDate = new Date();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(final boolean available) {
        this.available = available;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(final Book book) {
        this.book = book;
    }
}
