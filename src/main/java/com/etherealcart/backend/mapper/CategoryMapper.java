package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.*;
import com.etherealcart.backend.model.Category;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryResponseDTO toResponseDTO(Category category) {
        if (category == null) return null;

        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setImage(category.getImage());

        if (category.getProducts() != null) {
            // Map products to full ProductResponseDTOs (ProductMapper handles category -> summary to avoid recursion)
            List<ProductResponseDTO> productDTOs = category.getProducts().stream()
                    .map(ProductMapper::toResponseDTO)
                    .collect(Collectors.toList());
            dto.setProducts(productDTOs);
        } else {
            dto.setProducts(Collections.emptyList());
        }

        return dto;
    }

    public static CategorySummaryDTO toSummaryDTO(Category category) {
        if (category == null) return null;

        CategorySummaryDTO dto = new CategorySummaryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        dto.setImage(category.getImage());
        return dto;
    }

    public static Category toEntity(CategoryCreateRequestDTO dto) {
        if (dto == null) return null;
        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setImage(dto.getImage());
        return category;
    }

    /**
     * Update existing entity with non-null values from the update DTO (safe for PATCH-style updates).
     */
    public static void updateEntity(Category category, CategoryUpdateRequestDTO dto) {
        if (category == null || dto == null) return;
        if (dto.getName() != null) category.setName(dto.getName());
        if (dto.getSlug() != null) category.setSlug(dto.getSlug());
        if (dto.getDescription() != null) category.setDescription(dto.getDescription());
        if (dto.getImage() != null) category.setImage(dto.getImage());
    }

    public static List<CategoryResponseDTO> toResponseDTOList(List<Category> categories) {
        if (categories == null) return Collections.emptyList();
        return categories.stream().map(CategoryMapper::toResponseDTO).collect(Collectors.toList());
    }

    public static List<CategorySummaryDTO> toSummaryDTOList(List<Category> categories) {
        if (categories == null) return Collections.emptyList();
        return categories.stream().map(CategoryMapper::toSummaryDTO).collect(Collectors.toList());
    }
}
