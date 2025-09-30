package com.etherealcart.backend.dto;

import java.util.List;
import java.util.Map;

public class ProductCreateRequestDTO {
    private String name;
    private Double price;
    private Double originalPrice;
    private String description;
    private Integer stockQuantity;
    private Long categoryId; // reference instead of full entity
    private List<String> images;
    private List<String> tags;
    private Map<String, String> specifications;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public Double getOriginalPrice() { return originalPrice; }
    public void setOriginalPrice(Double originalPrice) { this.originalPrice = originalPrice; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getStockQuantity() { return stockQuantity; }
    public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }

    public Long getCategoryId() { return categoryId; }
    public void setCategoryId(Long categoryId) { this.categoryId = categoryId; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public Map<String, String> getSpecifications() { return specifications; }
    public void setSpecifications(Map<String, String> specifications) { this.specifications = specifications; }
}
