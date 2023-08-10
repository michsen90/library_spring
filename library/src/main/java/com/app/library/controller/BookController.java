package com.app.library.controller;

import com.app.library.model.Book;
import com.app.library.model.BookItem;
import com.app.library.model.RentBook;
import com.app.library.model.User;
import com.app.library.payload.request.RentBookRequest;
import com.app.library.repository.BookItemRepository;
import com.app.library.repository.BookRepository;
import com.app.library.repository.RentBookRepository;
import com.app.library.repository.UserRepository;
import com.app.library.service.RentBookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book")
public class BookController {

    private BookRepository bookRepository;
    private BookItemRepository bookItemRepository;

    private RentBookService rentBookService;
    private final UserRepository userRepository;
    private final RentBookRepository rentBookRepository;

    BookController(final BookRepository bookRepository, final BookItemRepository bookItemRepository, final RentBookService rentBookService,
                   final UserRepository userRepository,
                   final RentBookRepository rentBookRepository) {
        this.bookRepository = bookRepository;
        this.bookItemRepository = bookItemRepository;
        this.rentBookService = rentBookService;
        this.userRepository = userRepository;
        this.rentBookRepository = rentBookRepository;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Book with provided ISBN doesn't exist!");
        }
        return ResponseEntity.ok(book);
    }

    @PostMapping("/rent_book/{bookItemId}")
    public ResponseEntity<?> rentBook(@PathVariable Long bookItemId, @RequestBody RentBookRequest rentBookRequest) {
        RentBook newBook = new RentBook();
        newBook.setStartDate(rentBookRequest.getStartDate());
        newBook.setEndDate(rentBookRequest.getEndDate());
        if (!bookItemRepository.existsById(bookItemId)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Book Item not found!");
        }
        BookItem bookItem = bookItemRepository.findById(bookItemId).get();
        newBook.setBookItem(bookItem);
        if (!userRepository.existsById(rentBookRequest.getUserId())){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found!");
        }
        newBook.setUser(userRepository.findById(rentBookRequest.getUserId()).get());

        RentBook rentedBook = rentBookService.makeRentBook(newBook);
        if (rentedBook == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Something went wrong, book is not rent!");
        }
        bookItem.setAvailable(false);

        BookItem updatedBookItem = bookItemRepository.save(bookItem);
        return ResponseEntity.ok(rentedBook);
    }

}
