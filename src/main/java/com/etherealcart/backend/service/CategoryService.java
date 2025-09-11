package com.etherealcart.backend.service;

import com.etherealcart.backend.model.Category;
import com.etherealcart.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category create(Category category) {
        if (category.getName() == null || category.getSlug() == null) {
            throw new IllegalArgumentException("Name and slug are required");
        }
        return categoryRepository.save(category);
    }

    public List<Category> list() {
        return categoryRepository.findAll();
    }

    public List<Category> listWithProducts() {
        return categoryRepository.findCategoriesWithProducts();
    }

    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getBySlug(String slug) {
        return categoryRepository.findBySlug(slug);
    }

    public Category update(Long id, Category updated) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + id));
        existing.setName(updated.getName() != null ? updated.getName() : existing.getName());
        existing.setSlug(updated.getSlug() != null ? updated.getSlug() : existing.getSlug());
        existing.setDescription(updated.getDescription() != null ? updated.getDescription() : existing.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    public List<Category> searchByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name);
    }
}


