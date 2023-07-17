package com.app.library.controller;
import com.app.library.model.Author;
import com.app.library.model.Book;
import com.app.library.model.User;
import com.app.library.payload.request.NewAuthor;
import com.app.library.payload.request.NewBook;
import com.app.library.payload.response.MessageResponse;
import com.app.library.repository.AuthorRepository;
import com.app.library.repository.BookRepository;
import com.app.library.repository.CategoryRepository;
import com.app.library.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    ModeratorController(final AuthorRepository authorRepository, final BookRepository bookRepository, final CategoryRepository categoryRepository,
                        final UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/author")
    public ResponseEntity<?> addAuthor(@Valid @RequestBody NewAuthor newAuthor){
        Optional<Author> searchedAuthor = authorRepository.findByFirstnameAndLastname(newAuthor.getFirstname(), newAuthor.getLastname());
        if (searchedAuthor != null){
            return ResponseEntity.badRequest().build();
        }
        Author authorToSave = new Author(newAuthor.getFirstname(), newAuthor.getLastname());
        authorRepository.save(authorToSave);
        return ResponseEntity.ok(new MessageResponse("Author has been saved!"));
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id){
        Optional<Author> authorToDelete = authorRepository.findById(id);
        if(authorToDelete == null){
            return ResponseEntity.notFound().build();
        }
        authorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/book")
    public ResponseEntity<?> addBook(@RequestBody NewBook newBook){
        Optional<Book> searchedBook = bookRepository.findByISBN(newBook.getISBN());
        if (!searchedBook.equals(Optional.empty())){
            return ResponseEntity.badRequest().build();
        }
        Book book = new Book(newBook.getTitle(), newBook.getISBN());
        book.addAuthor(newBook.getAuthor());
        book.addCategory(newBook.getCategory());
        bookRepository.save(book);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody Book book){
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate == null){
            return ResponseEntity.notFound().build();
        }
        bookToUpdate.ifPresent(bookToSave ->{
            bookToSave.setTitle(book.getTitle());
            bookToSave.setISBN(book.getISBN());
            bookToSave.setAuthors(book.getAuthors());
            bookToSave.setCategories(book.getCategories());
            bookRepository.save(bookToSave);
        });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        Optional<Book> book = bookRepository.findById(id);
        if (book == null){
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
