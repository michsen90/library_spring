package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.*;
import com.app.library.payload.request.NewAuthor;
import com.app.library.repository.AuthorRepository;
import com.app.library.repository.BookRepository;
import com.app.library.repository.CategoryRepository;
import com.app.library.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ModeratorControllerTest extends LibraryApplicationTests {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookService bookService;

    @Autowired
    BookRepository bookRepository;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTE2OTM4NjksImV4cCI6MTY5MTc4MDI2OX0.RXU93jC7vXCr7G8rLPf_a0KEKx4fBFs43G9GD48Zejs";

    @Test
    void createAuthorAndAuthorExistThenReturn400() throws Exception{
        String uri = "/moderator/author";
        NewAuthor newAuthor = new NewAuthor();
        newAuthor.setFirstname("Andrzej");
        newAuthor.setLastname("Sapkowski");
        String inputJson = super.mapToJson(newAuthor);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void createAuthorAndReturnOk200() throws Exception{
        authorRepository.findByFirstnameAndLastname("New", "Author").ifPresent(author -> authorRepository.delete(author));
        String uri = "/moderator/author";
        NewAuthor newAuthor = new NewAuthor();
        newAuthor.setFirstname("New");
        newAuthor.setLastname("Author");
        String inputJson = super.mapToJson(newAuthor);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Author") && mvcResult.getResponse().getContentAsString().contains("New"));
    }

    @Test
    void deleteAuthorAndAuthorDoesntExistThenReturn404() throws Exception{
        String uri = "/moderator/author/{id}";
        Long id = 1000L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void deleteAuthorAndReturnNoContent204() throws Exception{
        Author savedAuthor = authorRepository.save(new Author("Author", "ToDelete"));
        String uri = "/moderator/author/{id}";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, savedAuthor.getId())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void createBookAndBookExistThenReturn400() throws Exception{
        String uri = "/moderator/book";
        Book newBook = new Book();
        newBook.setTitle("Sezon burz");
        newBook.setISBN("9788842932796");

        String inputJson = super.mapToJson(newBook);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void createBookAndReturnOk200() throws Exception{
        Optional<Book> bookToDelete = bookRepository.findByISBN("999999999999999");
        bookToDelete.ifPresent(book -> bookService.deleteBook(book.getId()));

        String uri = "/moderator/book";
        Book book = new Book();
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findById(1L).get());
        Set<Author> authors = new HashSet<>();
        authors.add(authorRepository.findById(1L).get());
        book.setTitle("Book to delete");
        book.setISBN("999999999999999");
        book.setAuthors(authors);
        book.setCategories(categories);

        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Book to delete") && mvcResult.getResponse().getContentAsString().contains("999999999999999"));
    }

    @Test
    @Transactional
    void updateBookAndReturn200() throws Exception{
        Optional<Book> bookToDelete = bookRepository.findByISBN("11111111111111");
        bookToDelete.ifPresent(book -> bookService.deleteBook(book.getId()));
        Book bookToUpdate = new Book();
        Set<Author> authors = new HashSet<>();
        authors.add(authorRepository.findById(1L).orElseThrow());
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findById(1L).orElseThrow());
        bookToUpdate.setTitle("Book to update");
        bookToUpdate.setISBN("11111111111111");
        bookToUpdate.setAuthors(authors);
        bookToUpdate.setCategories(categories);
        Book book = bookRepository.save(bookToUpdate);

        String uri = "/moderator/book/{id}";
        book.setTitle("Changed title");
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, book.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Changed title"));
    }

    @Test
    void updateBookAndAndReturn404() throws Exception{

        String uri = "/moderator/book/{id}";
        Long id =  1000L;
        Book book = new Book();
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    @Transactional
    void updateAuthorsForBookAndReturn200() throws Exception{
        Optional<Book> bookToDelete = bookRepository.findByISBN("22222222222222222");
        bookToDelete.ifPresent(book -> bookService.deleteBook(book.getId()));
        Book bookToUpdate = new Book();
        Set<Author> authors = new HashSet<>();
        authors.add(authorRepository.findById(1L).orElseThrow());
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findById(1L).orElseThrow());
        bookToUpdate.setTitle("Book to update");
        bookToUpdate.setISBN("22222222222222222");
        bookToUpdate.setAuthors(authors);
        bookToUpdate.setCategories(categories);
        Book book = bookRepository.save(bookToUpdate);
        String uri = "/moderator/book/authors/{id}";

        book.addAuthor(authorRepository.findById(2L).orElseThrow());
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, book.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Wagner"));
    }

    @Test
    void updateAuthorsForBookAndBookNotFoundThenReturn404() throws Exception{

        String uri = "/moderator/book/authors/{id}";
        Long id =  1000L;
        Book book = new Book();
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    @Transactional
    void updateCategoriesForBookAndReturn200() throws Exception{
        Book bookToUpdate = new Book();
        if(!bookRepository.existsByISBN("999999999999999")){
            Book bookForUpdating = new Book();
            Set<Author> authors = new HashSet<>();
            authors.add(authorRepository.findById(1L).orElseThrow());
            Set<Category> categories = new HashSet<>();
            categories.add(categoryRepository.findById(1L).get());
            bookForUpdating.setTitle("Book to update");
            bookForUpdating.setISBN("22222222222222222");
            bookForUpdating.setAuthors(authors);
            bookForUpdating.setCategories(categories);
            bookToUpdate = bookRepository.save(bookForUpdating);
        } else {
            bookToUpdate = bookRepository.findByISBN("999999999999999").get();
        }
        bookToUpdate.addCategory(categoryRepository.findById(3L).get());
        String uri = "/moderator/book/categories/{id}";

        String inputJson = super.mapToJson(bookToUpdate);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, bookToUpdate.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("COMIC"));
    }

    @Test
    void updateCategoriesForBookAndBookNotFoundThenReturn404() throws Exception{

        String uri = "/moderator/book/categories/{id}";
        Long id =  1000L;
        Book book = new Book();
        String inputJson = super.mapToJson(book);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void deleteBookAndBookNotFoundThenReturn404() throws Exception{

        String uri = "/moderator/book/{id}";
        Long id =  1000L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void deleteBookAndReturn204() throws Exception{

        String uri = "/moderator/book/{id}";
        Book book = new Book();
        if (!bookRepository.existsByISBN("999999999999999")){
            Set<Category> categories = new HashSet<>();
            categories.add(categoryRepository.findById(1L).orElseThrow());
            Set<Author> authors = new HashSet<>();
            authors.add(authorRepository.findById(1L).orElseThrow());
            book.setTitle("Book to delete");
            book.setISBN("999999999999999");
            book.setAuthors(authors);
            book.setCategories(categories);
            bookRepository.save(book);
        }
        book = bookRepository.findByISBN("999999999999999").get();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, book.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void getAllUsersAndReturn200() throws Exception{

        String uri = "/moderator/users/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void getBookByISBNAndReturn200True() throws Exception {
        String uri = "/moderator/book/ISBN/{ISBN}";
        String ISBN = "978-83-61187-44-8";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, ISBN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(status, 200);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("True"));
    }

    @Test
    void getBookByISBNAndReturn200False() throws Exception {
        String uri = "/moderator/book/ISBN/{ISBN}";
        String ISBN = "9788842932796";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, ISBN)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(status, 200);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("False"));
    }
}