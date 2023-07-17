package com.app.library.payload.request;


import com.app.library.model.Author;
import com.app.library.model.Category;
import jakarta.validation.constraints.NotBlank;


public class NewBook {

    @NotBlank
    private String title;

    @NotBlank
    private String ISBN;

    @NotBlank
    private Author author;

    @NotBlank
    private Category category;

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public Author getAuthor() {
        return author;
    }

    public Category getCategory() {
        return category;
    }
}
