package com.app.library.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "rent_book")
public class RentBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date startDate;
    private Date endDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_item_id")
    private BookItem bookItem;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    User user;

    public RentBook(final Date startDate, final Date endDate, final BookItem bookItem, final User user) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookItem = bookItem;
        this.user = user;
    }

    public RentBook() {}

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(final Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(final Date endDate) {
        this.endDate = endDate;
    }

    public BookItem getBookItem() {
        return bookItem;
    }

    public void setBookItem(final BookItem bookItem) {
        this.bookItem = bookItem;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }
}
