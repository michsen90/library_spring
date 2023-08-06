package com.app.library.payload.request;

import com.app.library.model.Author;
import com.app.library.model.BookItem;
import com.app.library.model.Category;

import java.util.List;
import java.util.Set;

public class UpdateAllBookFields {
    private Long id;
    private String title;
    private String isbn;
    private Set<Author> authors;
    private Set<Category> categories;
    private List<BookItem> bookItems;
    private int numberOfNewBook;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public List<BookItem> getBookItems() {
        return bookItems;
    }

    public int getNumberOfNewBook() {
        return numberOfNewBook;
    }
}
