package com.etherealcart.backend.dto;

public class CategorySummaryDTO {
    private Long id;
    private String name;
    private String slug;
    private String image;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }
}
