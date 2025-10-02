package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.*;
import com.etherealcart.backend.model.Category;
import com.etherealcart.backend.model.Product;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {

    public static ProductResponseDTO toResponseDTO(Product product) {
        if (product == null) return null;

        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setDescription(product.getDescription());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImages(product.getImages());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setInStock(product.getInStock());
        dto.setFeatured(product.getFeatured());
        dto.setTags(product.getTags());
        dto.setSpecifications(product.getSpecifications());

        // include a lightweight category (summary) to avoid recursion
        if (product.getCategory() != null) {
            dto.setCategory(CategoryMapper.toSummaryDTO(product.getCategory()));
        }

        return dto;
    }

    public static ProductSummaryDTO toSummaryDTO(Product product) {
        if (product == null) return null;

        ProductSummaryDTO dto = new ProductSummaryDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            dto.setImage(product.getImages().get(0));
        }
        dto.setRating(product.getRating());
        dto.setInStock(product.getInStock());
        return dto;
    }

    public static Product toEntity(ProductCreateRequestDTO dto, Category category) {
        if (dto == null) return null;

        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setDescription(dto.getDescription());
        product.setStockQuantity(dto.getStockQuantity() != null ? dto.getStockQuantity() : 0);
        product.setCategory(category);
        product.setImages(dto.getImages() != null ? dto.getImages() : new ArrayList<>());
        product.setTags(dto.getTags() != null ? dto.getTags() : new ArrayList<>());
        product.setSpecifications(dto.getSpecifications() != null ? dto.getSpecifications() : new HashMap<>());
        // rating, reviewCount, inStock, featured stay as entity defaults unless you explicitly set them
        return product;
    }

    /**
     * Update existing entity with non-null values from the update DTO.
     * If categoryId is provided, you should resolve the Category entity (outside this mapper) and pass it in.
     */
    public static void updateEntity(Product product, ProductUpdateRequestDTO dto, Category category) {
        if (product == null || dto == null) return;
        if (dto.getName() != null) product.setName(dto.getName());
        if (dto.getPrice() != null) product.setPrice(dto.getPrice());
        if (dto.getOriginalPrice() != null) product.setOriginalPrice(dto.getOriginalPrice());
        if (dto.getDescription() != null) product.setDescription(dto.getDescription());
        if (dto.getStockQuantity() != null) product.setStockQuantity(dto.getStockQuantity());
        if (dto.getCategoryId() != null && category != null) product.setCategory(category);
        if (dto.getImages() != null) product.setImages(dto.getImages());
        if (dto.getTags() != null) product.setTags(dto.getTags());
        if (dto.getSpecifications() != null) product.setSpecifications(dto.getSpecifications());
    }

    public static List<ProductResponseDTO> toResponseDTOList(List<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream().map(ProductMapper::toResponseDTO).collect(Collectors.toList());
    }

    public static List<ProductSummaryDTO> toSummaryDTOList(List<Product> products) {
        if (products == null) return Collections.emptyList();
        return products.stream().map(ProductMapper::toSummaryDTO).collect(Collectors.toList());
    }
}
