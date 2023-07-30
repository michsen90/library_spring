package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.Book;
import com.app.library.model.BookItem;
import com.app.library.payload.request.LoginRequest;
import com.app.library.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


class BookControllerTest extends LibraryApplicationTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTA3MTA2NDcsImV4cCI6MTY5MDc5NzA0N30.M9PUU7INyH1dIt6lpDUKaGC1i7wH1u8D6gL3n0pH6qQ";

    @Test
    void getAllBooksandReturn200() throws Exception{
        String uri = "/book/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Book[] books = super.mapFromJson(content, Book[].class);
        assertTrue(books.length > 0);
    }

    @Test
    @Transactional
    void getBooksByTitleAndReturn200() throws Exception{
        String uri = "/book/{title}";
        String title = "Sezon burz";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, title)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains(title));
    }

    @Test
    @Transactional
    void getBooksByTitleAndNotFound404() throws Exception{
        String uri = "/book/{title}";
        String title = "Doesnt exists this book";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, title)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getBookByISBNandReturn200() throws Exception {
        String uri = "/book/ISBN/{ISBN}";
        String ISBN = "9788842932796";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, ISBN)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }

    @Test
    void getBookByISBNandNotFoundReturn404() throws Exception {
        String uri = "/book/ISBN/{ISBN}";
        String ISBN = "999999DoesntExist";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, ISBN)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void rentBookAndBookDoesntExistThenReturn404() throws Exception {
        String uri = "/book/{id}/rent_book";
        Long id = 1000L;
        BookItem bookItem = new BookItem();
        bookItem.setCreatedDate(new Date());
        String bookItemJson = super.mapToJson(bookItem);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri, id)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(bookItemJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void rentBookAndBookExistsMakeRentThenReturn200() throws Exception {
        String uri = "/book/{id}/rent_book";
        Long id = 1L;
        BookItem bookItem = new BookItem();
        bookItem.setCreatedDate(new Date());
        String bookItemJson = super.mapToJson(bookItem);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri, id)
                .accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON).content(bookItemJson)
                .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
        ).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
    }
}