package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.Author;
import com.app.library.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class AuthorControllerTest extends LibraryApplicationTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTA3MTA2NDcsImV4cCI6MTY5MDc5NzA0N30.M9PUU7INyH1dIt6lpDUKaGC1i7wH1u8D6gL3n0pH6qQ";

    @Test
    void getAllAuthorsAndReturn200() throws Exception{
        String uri = "/author/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Author[] authors = super.mapFromJson(content, Author[].class);
        assertTrue(authors.length > 0);
    }

    @Test
    void getAuthorByIdAndNotFoundReturn404() throws Exception {
        String uri = "/author/{id}";
        Long id = 1000L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

    }

    @Test
    void getAuthorByIdAndReturn200() throws Exception {
        String uri = "/author/{id}";
        Long id = 1L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sapkowski"));
    }

    @Test
    void getAuthorByLastnameAndNotFoundReturn404() throws Exception {
        String uri = "/author/byLastname/{lastname}";
        String lastname = "Doesnt exist";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, lastname).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

    }

    @Test
    void getAuthorByLastnameAndReturn200() throws Exception {
        String uri = "/author/byLastname/{lastname}";
        String lastname = "Sapkowski";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, lastname).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Sapkowski"));
    }

}