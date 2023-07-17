package com.app.library.repository;

import com.app.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByTitle(String title);
    Optional<Book> findByISBN(String ISBN);
}
