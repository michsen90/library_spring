package com.app.library.repository;

import com.app.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.Set;


public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Set<Author> findByLastname(String lastname);
    Optional<Author> findByFirstnameAndLastname(String firstname, String lastname);
}
