package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.Author;
import com.app.library.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest extends LibraryApplicationTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTA3MTA2NDcsImV4cCI6MTY5MDc5NzA0N30.M9PUU7INyH1dIt6lpDUKaGC1i7wH1u8D6gL3n0pH6qQ";

    @Test
    void getAllCategoriesAndReturnOk200() throws Exception{
        String uri = "/category/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        Category[] categories = super.mapFromJson(content, Category[].class);
        assertTrue(categories.length > 0);
    }

}