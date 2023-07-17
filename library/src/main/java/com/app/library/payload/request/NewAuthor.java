package com.app.library.payload.request;

import jakarta.validation.constraints.NotBlank;

public class NewAuthor {

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(final String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(final String lastname) {
        this.lastname = lastname;
    }
}
