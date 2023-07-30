package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.ERole;
import com.app.library.model.User;
import com.app.library.payload.request.LoginRequest;
import com.app.library.payload.request.SignupRequest;
import com.app.library.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest extends LibraryApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Test
    void callWhenUnauthenticatedThen401() throws Exception {
        String uri = "/book/all";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(401, status);
    }

    @Test
    void authorizeUserWhenPassedCorrectDataThen200() throws Exception{
        String uri = "/api/auth/signin";
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("michal");
        loginRequest.setPassword("michal");

        String inputJson = super.mapToJson(loginRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        String content = mvcResult.getResponse().getContentAsString();
        assertTrue(content.contains(loginRequest.getUsername()));

    }

    @Test
    void signupUserUsernameExistAndReturnBadRequest400() throws Exception {
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("user");
        signupRequest.setEmail("user@gmail.com");
        signupRequest.setPassword("user123");
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        signupRequest.setRole(roles);
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void signupUserEmailExistAndReturnBadRequest400() throws Exception {
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("user1");
        signupRequest.setEmail("user@gmail.com");
        signupRequest.setPassword("user123");
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        signupRequest.setRole(roles);
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
    }

    @Test
    void signupUserWithNoProvidedRolesAndReturnOk200() throws Exception {
        User user = userRepository.findByUsername("testuser1").orElseThrow();
        userRepository.delete(user);
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser1");
        signupRequest.setEmail("testuser1@gmail.com");
        signupRequest.setPassword("testuser1");
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(
                mvcResult.getResponse().getContentAsString().contains("USER") &&
                        !mvcResult.getResponse().getContentAsString().contains("ADMIN") &&
                        !mvcResult.getResponse().getContentAsString().contains("MODERATOR")
                );
    }

    @Test
    void signupUserWithUserRoleAndReturnOk200() throws Exception {
        User user = userRepository.findByUsername("testuser1").orElseThrow();
        userRepository.delete(user);
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser1");
        signupRequest.setEmail("testuser1@gmail.com");
        signupRequest.setPassword("testuser1");
        Set<String> roles = new HashSet<>();
        roles.add("USER");
        signupRequest.setRole(roles);
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(
                mvcResult.getResponse().getContentAsString().contains("USER") &&
                        !mvcResult.getResponse().getContentAsString().contains("ADMIN") &&
                        !mvcResult.getResponse().getContentAsString().contains("MODERATOR")
        );
    }

    @Test
    void signupUserWithModeratorRoleAndReturnOk200() throws Exception {
        User user = userRepository.findByUsername("testuser1").orElseThrow();
        userRepository.delete(user);
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser1");
        signupRequest.setEmail("testuser1@gmail.com");
        signupRequest.setPassword("testuser1");
        Set<String> roles = new HashSet<>();
        roles.add("MODERATOR");
        signupRequest.setRole(roles);
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(
                !mvcResult.getResponse().getContentAsString().contains("USER") &&
                        !mvcResult.getResponse().getContentAsString().contains("ADMIN") &&
                        mvcResult.getResponse().getContentAsString().contains("MODERATOR")
        );
    }

    @Test
    void signupUserWithAdminRoleAndReturnOk200() throws Exception {
        User user = userRepository.findByUsername("testuser1").orElseThrow();
        userRepository.delete(user);
        String uri = "/api/auth/signup";
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setUsername("testuser1");
        signupRequest.setEmail("testuser1@gmail.com");
        signupRequest.setPassword("testuser1");
        Set<String> roles = new HashSet<>();
        roles.add("ADMIN");
        signupRequest.setRole(roles);
        String inputJson = super.mapToJson(signupRequest);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(
                !mvcResult.getResponse().getContentAsString().contains("USER") &&
                        mvcResult.getResponse().getContentAsString().contains("ADMIN") &&
                        !mvcResult.getResponse().getContentAsString().contains("MODERATOR")
        );
    }

}