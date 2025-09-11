package com.etherealcart.backend.repository;

import com.etherealcart.backend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
    
    List<Order> findByStatus(String status);
    
    List<Order> findByUserIdAndStatus(Long userId, String status);
    
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items WHERE o.user.id = :userId")
    List<Order> findOrdersWithItemsByUserId(@Param("userId") Long userId);
    
    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
