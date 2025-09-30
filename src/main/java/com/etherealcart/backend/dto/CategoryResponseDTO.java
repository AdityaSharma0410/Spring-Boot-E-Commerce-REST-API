package com.etherealcart.backend.dto;

import java.util.List;

public class CategoryResponseDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;

    // Optional: include product summaries if needed
    private List<ProductResponseDTO> products;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public List<ProductResponseDTO> getProducts() { return products; }
    public void setProducts(List<ProductResponseDTO> products) { this.products = products; }
}
