package com.etherealcart.backend.repository;

import com.etherealcart.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Find products by category slug
    @Query("SELECT p FROM Product p WHERE p.category.slug = :slug")
    List<Product> findByCategorySlug(@Param("slug") String slug);
    
    // Find featured products
    List<Product> findByFeaturedTrue();
    
    // Find products in stock
    List<Product> findByInStockTrue();
    
    // Find products by price range
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);
    
    // Find products by stock status
    List<Product> findByInStock(Boolean inStock);
    
    // Search products by name or description
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Product> searchProducts(@Param("search") String search);
    
    // Find related products (same category, excluding current product)
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId AND p.id != :productId")
    List<Product> findRelatedProducts(@Param("categoryId") Long categoryId, @Param("productId") Long productId);
    
    // Find products by category
    List<Product> findByCategoryId(Long categoryId);
    
    // Paginated search
    @Query("SELECT p FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Product> searchProductsPaginated(@Param("search") String search, Pageable pageable);
    
    // Find products with filters
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice) AND " +
           "(:inStock IS NULL OR p.inStock = :inStock) AND " +
           "(:featured IS NULL OR p.featured = :featured)")
    Page<Product> findProductsWithFilters(
        @Param("categoryId") Long categoryId,
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        @Param("inStock") Boolean inStock,
        @Param("featured") Boolean featured,
        Pageable pageable
    );
}
