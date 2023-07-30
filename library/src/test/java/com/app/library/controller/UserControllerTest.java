package com.app.library.controller;

import com.app.library.LibraryApplicationTests;
import com.app.library.model.Role;
import com.app.library.model.User;
import com.app.library.repository.RoleRepository;
import com.app.library.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest extends LibraryApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWNoYWwiLCJpYXQiOjE2OTA3MTA2NDcsImV4cCI6MTY5MDc5NzA0N30.M9PUU7INyH1dIt6lpDUKaGC1i7wH1u8D6gL3n0pH6qQ";


    @Test
    void getUserByIdAndUserNotFoundThenReturn404() throws Exception{
        String uri = "/user/{id}";
        Long id = 1000L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);
    }

    @Test
    void getUserByIdAndUserFoundThenReturn200() throws Exception{
        String uri = "/user/{id}";
        Long id = 4L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);
        assertTrue(mvcResult.getResponse().getContentAsString().contains("adam"));
    }

    @Test
    void updateUserAndUserNotFoundThenReturn404() throws Exception{
        String uri = "/user/{id}";
        Long id = 1000L;
        User user = new User();
        String inputJson = super.mapToJson(user);
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
    void updateUserAndUserFoundThenReturn204() throws Exception{
        userRepository.findByUsername("Changed username").ifPresent(user -> {
            userRepository.delete(user);
        });
        String uri = "/user/{id}";
        User user = new User();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).orElseThrow());
        user.setUsername("userToUpdate");
        user.setEmail("userToUpdate@gmail.com");
        user.setPassword("userToUpdate");
        user.setRoles(roleSet);
        User userToUpdate = userRepository.save(user);

        userToUpdate.setUsername("Changed username");
        String inputJson = super.mapToJson(userToUpdate);
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(uri, userToUpdate.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inputJson))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);
    }

    @Test
    void deleteUserAndUserNotFoundThenReturn404() throws Exception {
        String uri = "/user/{id}";
        Long id = 1000L;
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, id)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(404, status);

    }

    @Test
    void deleteUserAndUserFoundThenReturn204() throws Exception {
        userRepository.findByUsername("userToDelete").ifPresent(user -> {
            userRepository.delete(user);
        });
        String uri = "/user/{id}";
        User userToDelete = new User();
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleRepository.findById(1L).orElseThrow());
        userToDelete.setUsername("userToDelete");
        userToDelete.setEmail("userToDelete@gmail.com");
        userToDelete.setPassword("userToDelete");
        userToDelete.setRoles(roleSet);
        User user = userRepository.save(userToDelete);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri, user.getId())
                        .header(HttpHeaders.AUTHORIZATION, "Bearer Bearer "+token)
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(204, status);

    }
}