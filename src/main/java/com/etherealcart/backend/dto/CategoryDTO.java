package com.etherealcart.backend.dto;

import java.util.List;

public class CategoryDTO {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String image;

    // Products inside this category (optional)
    private List<ProductDTO> products;

    public CategoryDTO() {}

    public CategoryDTO(Long id, String name, String slug, String description, String image, List<ProductDTO> products) {
        this.id = id;
        this.name = name;
        this.slug = slug;
        this.description = description;
        this.image = image;
        this.products = products;
    }

    // --- Getters & Setters ---
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

    public List<ProductDTO> getProducts() { return products; }
    public void setProducts(List<ProductDTO> products) { this.products = products; }
}
