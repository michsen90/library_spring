package com.app.library.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ERole name;

    public Role(){}

    Role(final ERole name) {
        this.name = name;
    }

    Long getId() {
        return id;
    }

    public ERole getName() {
        return name;
    }
}
