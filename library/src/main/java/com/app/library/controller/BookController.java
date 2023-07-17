package com.app.library.controller;

import com.app.library.model.Book;
import com.app.library.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookRepository bookRepository;

    BookController(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title){
        List<Book> books = bookRepository.findByTitle(title);
        if(books == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/ISBN/{ISBN}")
    public ResponseEntity<?> getBookByISBN(@PathVariable String ISBN){
        Optional<Book> book = bookRepository.findByISBN(ISBN);
        if (book == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }
}
