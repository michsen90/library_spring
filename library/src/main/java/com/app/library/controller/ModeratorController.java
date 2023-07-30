package com.app.library.controller;
import com.app.library.model.Author;
import com.app.library.model.Book;
import com.app.library.model.User;
import com.app.library.payload.request.NewAuthor;
import com.app.library.payload.response.MessageResponse;
import com.app.library.repository.AuthorRepository;
import com.app.library.repository.BookRepository;
import com.app.library.repository.CategoryRepository;
import com.app.library.repository.UserRepository;
import com.app.library.service.AuthorService;
import com.app.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private AuthorRepository authorRepository;

    private AuthorService authorService;
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private final BookService bookService;

    ModeratorController(final AuthorRepository authorRepository, final BookRepository bookRepository, final CategoryRepository categoryRepository,
                        final UserRepository userRepository, final AuthorService authorService, final BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @PostMapping("/author")
    public ResponseEntity<?> addAuthor(@Valid @RequestBody NewAuthor newAuthor){
        Optional<Author> searchedAuthor = authorRepository.findByFirstnameAndLastname(newAuthor.getFirstname(), newAuthor.getLastname());
        if (!searchedAuthor.equals(Optional.empty())){
            return ResponseEntity.badRequest().build();
        }
        Author authorToSave = new Author(newAuthor.getFirstname(), newAuthor.getLastname());
        Author author = authorRepository.save(authorToSave);
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        Optional<Author> authorToDelete = authorRepository.findById(id);
        if(authorToDelete.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        authorToDelete.ifPresent(author -> {
            authorService.deleteAuthor(author);
        });
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book")
    public ResponseEntity<?> addBook(@RequestBody Book newBook){
        Optional<Book> searchedBook = bookRepository.findByISBN(newBook.getISBN());
        if (!searchedBook.equals(Optional.empty())){
            return ResponseEntity.badRequest().build();
        }
        Book savedBook = bookService.createBook(newBook);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/book/authors/{id}")
    public ResponseEntity<?> updateAuthorsForBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        Book updatedBook = bookService.updateAuthorsForBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @PutMapping("/book/categories/{id}")
    public ResponseEntity<?> updateCategoriesForBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        Book updatedBook = bookService.updateCategoriesForBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if (book.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/all")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userRepository.findAll());
    }


}
