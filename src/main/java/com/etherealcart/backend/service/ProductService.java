package com.etherealcart.backend.service;
import com.etherealcart.backend.model.Product;
import com.etherealcart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        if (product.getName() == null || product.getPrice() == null || product.getStockQuantity() == null) {
            throw new IllegalArgumentException("Name, price, and stock quantity are required");
        }
        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        Product product = existingProduct.get();
        product.setName(updatedProduct.getName() != null ? updatedProduct.getName() : product.getName());
        product.setPrice(updatedProduct.getPrice() != null ? updatedProduct.getPrice() : product.getPrice());
        product.setDescription(updatedProduct.getDescription() != null ? updatedProduct.getDescription() : product.getDescription());
        product.setStockQuantity(updatedProduct.getStockQuantity() != null ? updatedProduct.getStockQuantity() : product.getStockQuantity());
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product not found with ID: " + id);
        }
        productRepository.deleteById(id);
    }

    // New functionality for frontend integration

    public List<Product> getFeaturedProducts() {
        return productRepository.findByFeaturedTrue();
    }

    public List<Product> getProductsByCategorySlug(String slug) {
        return productRepository.findByCategorySlug(slug);
    }

    public List<Product> getRelatedProducts(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + productId));
        return productRepository.findRelatedProducts(product.getCategory().getId(), productId);
    }

    public List<Product> searchProducts(String query) {
        return productRepository.searchProducts(query);
    }

    public Page<Product> findWithFilters(Long categoryId, Double minPrice, Double maxPrice,
                                         Boolean inStock, Boolean featured, Integer page, Integer size, String sort) {
        Pageable pageable = PageRequest.of(
                page != null ? page : 0,
                size != null ? size : 20,
                sort != null ? Sort.by(sort) : Sort.unsorted()
        );
        return productRepository.findProductsWithFilters(categoryId, minPrice, maxPrice, inStock, featured, pageable);
    }
}
