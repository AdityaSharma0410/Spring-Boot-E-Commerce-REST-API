package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.CategoryDTO;
import com.etherealcart.backend.dto.ProductDTO;
import com.etherealcart.backend.model.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    // === Entity -> DTO ===
    public static CategoryDTO toDTO(Category category, boolean includeProducts) {
        if (category == null) return null;

        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setImage(category.getImage());

        if (includeProducts && category.getProducts() != null) {
            dto.setProducts(
                    category.getProducts().stream()
                            .map(ProductMapper::toDTO)
                            .collect(Collectors.toList())
            );
        }

        return dto;
    }

    // === DTO -> Entity ===
    public static Category toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setImage(dto.getImage());

        // ⚠️ products skipped to avoid recursion
        return category;
    }
}
