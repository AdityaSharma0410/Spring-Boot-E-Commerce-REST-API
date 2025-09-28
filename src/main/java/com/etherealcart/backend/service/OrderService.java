package com.etherealcart.backend.service;


import com.etherealcart.backend.dto.OrderItemRequestDTO;
import com.etherealcart.backend.model.Order;
import com.etherealcart.backend.model.OrderItem;
import com.etherealcart.backend.model.Product;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.repository.OrderItemRepository;
import com.etherealcart.backend.repository.OrderRepository;
import com.etherealcart.backend.repository.ProductRepository;
import com.etherealcart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(Long userId, List<OrderItemRequestDTO> itemRequests) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        Order order = new Order();
        order.setUser(user.get());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PLACED");
        double totalAmount = 0.0;

        for (OrderItemRequestDTO itemRequest : itemRequests) {
            Optional<Product> product = productRepository.findById(itemRequest.getProductId());
            if (product.isEmpty()) {
                throw new IllegalArgumentException("Product not found with ID: " + itemRequest.getProductId());
            }
            if (itemRequest.getQuantity() <= 0) {
                throw new IllegalArgumentException("Quantity must be positive for product ID: " + itemRequest.getProductId());
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product.get());
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPriceAtPurchase(product.get().getPrice());
            order.getItems().add(orderItem);
            totalAmount += product.get().getPrice() * itemRequest.getQuantity();
        }
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findAll().stream()
                .filter(o -> o.getUser() != null && o.getUser().getId().equals(userId))
                .toList();
    }

    public Order updateOrderStatus(Long id, String status) {
        Optional<Order> existingOrder = orderRepository.findById(id);
        if (existingOrder.isEmpty()) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        Order order = existingOrder.get();
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new IllegalArgumentException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }
}

