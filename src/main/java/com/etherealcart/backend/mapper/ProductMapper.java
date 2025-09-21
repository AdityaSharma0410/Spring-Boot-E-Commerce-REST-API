package com.etherealcart.backend.mapper;

import com.etherealcart.backend.dto.ProductDTO;
import com.etherealcart.backend.model.Category;
import com.etherealcart.backend.model.Product;

public class ProductMapper {

    // === Entity -> DTO ===
    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setOriginalPrice(product.getOriginalPrice());
        dto.setStock(product.getStockQuantity());
        dto.setImages(product.getImages());
        dto.setRating(product.getRating());
        dto.setReviewCount(product.getReviewCount());
        dto.setInStock(product.getInStock());
        dto.setFeatured(product.getFeatured());
        dto.setTags(product.getTags());
        dto.setSpecifications(product.getSpecifications());

        if (product.getCategory() != null) {
            dto.setCategoryId(product.getCategory().getId());
            dto.setCategoryName(product.getCategory().getName());
        }

        return dto;
    }

    // === DTO -> Entity ===
    public static Product toEntity(ProductDTO dto, Category category) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setStockQuantity(dto.getStock());
        product.setImages(dto.getImages());
        product.setRating(dto.getRating());
        product.setReviewCount(dto.getReviewCount());
        product.setInStock(dto.getInStock());
        product.setFeatured(dto.getFeatured());
        product.setTags(dto.getTags());
        product.setSpecifications(dto.getSpecifications());

        product.setCategory(category); // assign category entity

        return product;
    }

    // === DTO -> Entity (without category) ===
    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;

        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setOriginalPrice(dto.getOriginalPrice());
        product.setStockQuantity(dto.getStock());
        product.setImages(dto.getImages());
        product.setRating(dto.getRating());
        product.setReviewCount(dto.getReviewCount());
        product.setInStock(dto.getInStock());
        product.setFeatured(dto.getFeatured());
        product.setTags(dto.getTags());
        product.setSpecifications(dto.getSpecifications());

        return product;
    }
}
