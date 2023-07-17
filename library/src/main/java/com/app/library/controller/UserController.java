package com.app.library.controller;

import com.app.library.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.app.library.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        if (userRepository.findById(id) == null){
            return ResponseEntity.notFound().build();
        }
        userRepository.findById(id).ifPresent(u -> {
            u.setUsername(user.getUsername());
            u.setEmail(user.getEmail());
            userRepository.save(u);
        });
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if (userRepository.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        userRepository.findById(id).ifPresent(user -> {
            userRepository.delete(user);
        });
        return ResponseEntity.noContent().build();
    }
}
