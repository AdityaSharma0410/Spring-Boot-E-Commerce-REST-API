package com.etherealcart.backend.service;

import com.etherealcart.backend.dto.ProductDTO;
import com.etherealcart.backend.exceptions.ExceptionFactory;
import com.etherealcart.backend.mapper.ProductMapper;
import com.etherealcart.backend.model.Category;
import com.etherealcart.backend.model.Product;
import com.etherealcart.backend.repository.CategoryRepository;
import com.etherealcart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductDTO createProduct(ProductDTO productDTO) {
        if (productDTO.getName() == null || productDTO.getPrice() == null || productDTO.getCategoryId() == null) {
            throw ExceptionFactory.missingProductFields("Name, price, and category are required");
        }

        if (productDTO.getPrice() != null && productDTO.getPrice() <= 0) {
            throw ExceptionFactory.invalidProductPrice(productDTO.getPrice());
        }

        if (!categoryRepository.existsById(productDTO.getCategoryId())) {
            throw ExceptionFactory.categoryNotFound(productDTO.getCategoryId());
        }

        // Get category entity
        Category category = categoryRepository.findById(productDTO.getCategoryId())
                .orElseThrow(() -> ExceptionFactory.categoryNotFound(productDTO.getCategoryId()));

        // Convert DTO to entity with category
        Product product = ProductMapper.toEntity(productDTO, category);
        Product saved = productRepository.save(product);
        return ProductMapper.toDTO(saved);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(ProductMapper::toDTO);
    }

    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existing -> {
                    // Update basic fields
                    if (productDTO.getName() != null) {
                        existing.setName(productDTO.getName());
                    }
                    if (productDTO.getPrice() != null) {
                        if (productDTO.getPrice() <= 0) {
                            throw ExceptionFactory.invalidProductPrice(productDTO.getPrice());
                        }
                        existing.setPrice(productDTO.getPrice());
                    }
                    if (productDTO.getDescription() != null) {
                        existing.setDescription(productDTO.getDescription());
                    }
                    if (productDTO.getStock() != null) {
                        existing.setStockQuantity(productDTO.getStock());
                    }
                    if (productDTO.getImages() != null) {
                        existing.setImages(productDTO.getImages());
                    }
                    if (productDTO.getFeatured() != null) {
                        existing.setFeatured(productDTO.getFeatured());
                    }
                    if (productDTO.getInStock() != null) {
                        existing.setInStock(productDTO.getInStock());
                    }
                    if (productDTO.getTags() != null) {
                        existing.setTags(productDTO.getTags());
                    }
                    if (productDTO.getSpecifications() != null) {
                        existing.setSpecifications(productDTO.getSpecifications());
                    }
                    
                    // Handle category change if provided
                    if (productDTO.getCategoryId() != null && !productDTO.getCategoryId().equals(existing.getCategory().getId())) {
                        Category newCategory = categoryRepository.findById(productDTO.getCategoryId())
                                .orElseThrow(() -> ExceptionFactory.categoryNotFound(productDTO.getCategoryId()));
                        existing.setCategory(newCategory);
                    }
                    
                    return ProductMapper.toDTO(productRepository.save(existing));
                });
    }

    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<ProductDTO> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue()
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getProductsByCategorySlug(String slug) {
        return productRepository.findByCategorySlug(slug)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> getRelatedProducts(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> ExceptionFactory.productNotFound(productId));
        return productRepository.findRelatedProducts(product.getCategory().getId(), productId)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> searchProducts(String query) {
        return productRepository.searchProducts(query)
                .stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Page<ProductDTO> findWithFilters(Long categoryId, Double minPrice, Double maxPrice,
                                            Boolean inStock, Boolean featured,
                                            Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                sort != null ? Sort.by(sort) : Sort.unsorted()
        );
        return productRepository.findProductsWithFilters(categoryId, minPrice, maxPrice, inStock, featured, pageable)
                .map(ProductMapper::toDTO);
    }
}
