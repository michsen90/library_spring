package com.app.library.service;

import com.app.library.model.Author;
import com.app.library.model.Book;
import com.app.library.repository.AuthorRepository;
import com.app.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;

    AuthorService(final AuthorRepository authorRepository, final BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void deleteAuthor(Author author){
        Optional<List<Book>> books = bookRepository.findByAuthors(author);
        books.ifPresent(books1 -> {
            books1.forEach(book -> {
                bookRepository.deleteById(book.getId());
            });
        });
        authorRepository.delete(author);
    }
}
