package com.etherealcart.backend.dto;

import java.util.List;

// --- Request DTOs ---

public class CategoryCreateRequestDTO {
    private String name;
    private String slug;
    private String description;
    private String image;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}

