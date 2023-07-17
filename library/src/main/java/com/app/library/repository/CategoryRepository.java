package com.app.library.repository;

import com.app.library.model.Category;
import com.app.library.model.ECategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByBookType(ECategory bookType);
}
