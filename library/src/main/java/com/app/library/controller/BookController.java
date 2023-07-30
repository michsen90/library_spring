package com.app.library.controller;

import com.app.library.model.Book;
import com.app.library.model.BookItem;
import com.app.library.repository.BookItemRepository;
import com.app.library.repository.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookRepository bookRepository;
    private BookItemRepository bookItemRepository;

    BookController(final BookRepository bookRepository, final BookItemRepository bookItemRepository) {
        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAllBooks(){
        return ResponseEntity.ok(bookRepository.findAll());
    }

    @GetMapping("/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title){
        List<Book> books = bookRepository.findByTitle(title);
        if(books == null || books.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(books);
    }

    @GetMapping("/ISBN/{ISBN}")
    public ResponseEntity<?> getBookByISBN(@PathVariable String ISBN){
        Optional<Book> book = bookRepository.findByISBN(ISBN);
        if (book.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping("/{id}/rent_book")
    public ResponseEntity<?> rentBook(@PathVariable Long id, @RequestBody BookItem bookItem){
        Optional<Book> book = bookRepository.findById(id);
        if (book.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("Book has been rent!");
    }

}
