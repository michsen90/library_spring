package com.app.library.payload.request;


import com.app.library.model.Author;
import com.app.library.model.Category;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;


public class NewBook {

    @NotBlank
    private String title;

    @NotBlank
    private String ISBN;

    @NotBlank
    private Set<Author> author;

    @NotBlank
    private Set<Category> category;

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public Set<Author> getAuthor() {
        return author;
    }

    public Set<Category> getCategory() {
        return category;
    }
}
