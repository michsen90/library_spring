package com.app.library.service;

import com.app.library.model.Book;
import com.app.library.model.BookItem;
import com.app.library.repository.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private BookRepository bookRepository;

    BookService(final BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book createBook(Book newBook){
        Book bookToSave = new Book(newBook.getTitle(), newBook.getISBN(), newBook.getAuthors(), newBook.getCategories());
        bookToSave.addBookItem(new BookItem());
        Book savedBook = bookRepository.save(bookToSave);
        return savedBook;
    }

    public Book updateBook(Long id, Book book, int numberOfNewBook){
        Book bookToUpdate = bookRepository.findBookById(id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setISBN(book.getISBN());
        bookToUpdate.setAuthors(book.getAuthors());
        bookToUpdate.setCategories(book.getCategories());
        bookToUpdate = updateBookItemsForBook(id, numberOfNewBook);
        Book updatedBook = bookRepository.save(bookToUpdate);
        return updatedBook;
    }

    public Book updateAuthorsForBook(Long id, Book book){
        Book bookToUpdate = bookRepository.findBookById(id);
        bookToUpdate.setAuthors(book.getAuthors());
        Book updateBook = bookRepository.save(bookToUpdate);
        return updateBook;
    }

    public Book updateCategoriesForBook(Long id, Book book){
        Book bookToUpdate = bookRepository.findBookById(id);
        bookToUpdate.setCategories(book.getCategories());
        Book updateBook = bookRepository.save(bookToUpdate);
        return updateBook;
    }

    public Book updateBookItemsForBook(Long id, int numberOfNewBooks){
        Book bookToUpdate = bookRepository.findBookById(id);
        if (numberOfNewBooks == -1){
            return bookToUpdate;
        }
        for (int i = 0; i< numberOfNewBooks; i++){
            BookItem newBookItem = new BookItem();
            bookToUpdate.addBookItem(newBookItem);
        }
        Book updatedBook = bookRepository.save(bookToUpdate);
        return updatedBook;
    }

    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
}
