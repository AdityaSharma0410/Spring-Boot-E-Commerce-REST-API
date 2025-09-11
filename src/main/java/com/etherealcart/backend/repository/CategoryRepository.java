package com.etherealcart.backend.repository;

import com.etherealcart.backend.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findBySlug(String slug);
    
    List<Category> findByNameContainingIgnoreCase(String name);
    
    // Hibernate 6: use join or SIZE() instead of collection.size path
    @Query("SELECT DISTINCT c FROM Category c JOIN c.products p")
    List<Category> findCategoriesWithProducts();
    
    boolean existsBySlug(String slug);
}


