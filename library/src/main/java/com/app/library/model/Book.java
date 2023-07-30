package com.app.library.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = {
        @UniqueConstraint(columnNames = "ISBN")
})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "ISBN")
    private String ISBN;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "category_book", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @OneToMany(cascade = CascadeType.ALL)
    private List<BookItem> bookItems;

    public Book(){}

    public Book(final String title, final String ISBN, final Set<Author> authors, final Set<Category> categories) {
        this.title = title;
        this.ISBN = ISBN;
        this.authors = authors;
        this.categories = categories;
        this.bookItems = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(final String ISBN) {
        this.ISBN = ISBN;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(final Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(final Category category){ this.categories.add(category); }

    public void addAuthor(final Author author){ this.authors.add(author); }

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public void setBookItems(final List<BookItem> bookItems) {
        this.bookItems = bookItems;
    }

    public void addBookItem(final BookItem bookItem){ this.bookItems.add(bookItem); }
}
