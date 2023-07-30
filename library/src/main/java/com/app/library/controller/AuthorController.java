package com.app.library.controller;

import com.app.library.model.Author;
import com.app.library.repository.AuthorRepository;
import com.app.library.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/author")
public class AuthorController {

    private AuthorRepository authorRepository;
    private final UserRepository userRepository;

    AuthorController(final AuthorRepository authorRepository,
                     final UserRepository userRepository) {
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Author>> getAllAuthors(){
        return ResponseEntity.ok(authorRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAuthorById(@PathVariable Long id){
        Optional<Author> author = authorRepository.findById(id);
        if (author.equals(Optional.empty())){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @GetMapping("/byLastname/{lastname}")
    public ResponseEntity<Set<Author>> getAuthorsByLastname(@PathVariable String lastname){
        Set<Author> authors = authorRepository.findByLastname(lastname);
        if (authors.size() == 0){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authors);
    }

}
