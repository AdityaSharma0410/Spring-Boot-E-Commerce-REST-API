package com.etherealcart.backend.controller;

import com.etherealcart.backend.dto.CategoryDTO;
import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.exceptions.CategoryNotFoundException;
import com.etherealcart.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryDTO categoryDTO) {
        CategoryDTO saved = categoryService.create(categoryDTO);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> list() {
        return ResponseEntity.ok(categoryService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> get(@PathVariable String id) {
        try {
            Long categoryId = Long.parseLong(id);
            return categoryService.get(categoryId)
                    .map(ResponseEntity::ok)
                    .orElseThrow(() -> ExceptionFactory.categoryNotFound(categoryId));
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidCategoryId(id);
        }
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<CategoryDTO> getBySlug(@PathVariable String slug) {
        return categoryService.getBySlug(slug)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> ExceptionFactory.categoryNotFound(slug));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@PathVariable String id, @RequestBody CategoryDTO categoryDTO) {
        try {
            Long categoryId = Long.parseLong(id);
            CategoryDTO updated = categoryService.update(categoryId, categoryDTO);
            return ResponseEntity.ok(updated);
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidCategoryId(id);
        } catch (CategoryNotFoundException ex) {
            throw ex; // handled globally
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            Long categoryId = Long.parseLong(id);
            if (categoryService.get(categoryId).isEmpty()) {
                throw ExceptionFactory.categoryNotFound(categoryId);
            }
            categoryService.delete(categoryId);
            return ResponseEntity.noContent().build();
        } catch (NumberFormatException ex) {
            throw ExceptionFactory.invalidCategoryId(id);
        }
    }
}
