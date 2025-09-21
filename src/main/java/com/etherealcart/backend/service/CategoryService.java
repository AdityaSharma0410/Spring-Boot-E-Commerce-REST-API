package com.etherealcart.backend.service;

import com.etherealcart.backend.dto.CategoryDTO;
import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.mapper.CategoryMapper;
import com.etherealcart.backend.model.Category;
import com.etherealcart.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDTO create(CategoryDTO categoryDTO) {
        if (categoryDTO.getName() == null || categoryDTO.getSlug() == null) {
            throw ExceptionFactory.missingCategoryFields("Name and slug");
        }

        if (categoryRepository.findBySlug(categoryDTO.getSlug()).isPresent()) {
            throw ExceptionFactory.duplicateSlug(categoryDTO.getSlug());
        }

        Category category = CategoryMapper.toEntity(categoryDTO);
        Category saved = categoryRepository.save(category);
        return CategoryMapper.toDTO(saved, false);
    }

    public List<CategoryDTO> list() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> CategoryMapper.toDTO(c, false))
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> listWithProducts() {
        return categoryRepository.findCategoriesWithProducts()
                .stream()
                .map(c -> CategoryMapper.toDTO(c, true))
                .collect(Collectors.toList());
    }

    public Optional<CategoryDTO> get(Long id) {
        return categoryRepository.findById(id)
                .map(c -> CategoryMapper.toDTO(c, true));
    }

    public Optional<CategoryDTO> getBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .map(c -> CategoryMapper.toDTO(c, true));
    }

    public CategoryDTO update(Long id, CategoryDTO updatedDTO) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> ExceptionFactory.categoryNotFound(id));

        existing.setName(updatedDTO.getName() != null ? updatedDTO.getName() : existing.getName());
        existing.setSlug(updatedDTO.getSlug() != null ? updatedDTO.getSlug() : existing.getSlug());
        existing.setDescription(updatedDTO.getDescription() != null ? updatedDTO.getDescription() : existing.getDescription());
        existing.setImage(updatedDTO.getImage() != null ? updatedDTO.getImage() : existing.getImage());

        Category saved = categoryRepository.save(existing);
        return CategoryMapper.toDTO(saved, false);
    }

    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw ExceptionFactory.categoryNotFound(id);
        }
        categoryRepository.deleteById(id);
    }

    public List<CategoryDTO> searchByName(String name) {
        return categoryRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(c -> CategoryMapper.toDTO(c, false))
                .collect(Collectors.toList());
    }
}
