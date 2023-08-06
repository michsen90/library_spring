package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

class CategoryControllerTest extends LibraryApplicationTests {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTEwMDMzOTMsImV4cCI6MTY5MTA4OTc5M30.3Ksg2q8drnhkMEa2EyLaZPY8ZPuL8nXxDBt-8MI2Wco";

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