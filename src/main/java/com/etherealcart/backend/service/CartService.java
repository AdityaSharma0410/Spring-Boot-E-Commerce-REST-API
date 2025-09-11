package com.etherealcart.backend.service;


import com.etherealcart.backend.model.Cart;
import com.etherealcart.backend.model.User;
import com.etherealcart.backend.repository.CartRepository;
import com.etherealcart.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;

    public Cart createCart(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        // Prevent multiple carts per user
        Optional<Cart> existing = cartRepository.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get();
        }
        Cart cart = new Cart();
        cart.setUser(user.get());
        return cartRepository.save(cart);
    }

    public Optional<Cart> getCartById(Long id) {
        return cartRepository.findById(id);
    }

    public Optional<Cart> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Optional<Cart> getCartWithItemsByUserId(Long userId) {
        return cartRepository.findCartWithItemsByUserId(userId);
    }

    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }

    public void deleteCart(Long id) {
        if (!cartRepository.existsById(id)) {
            throw new IllegalArgumentException("Cart not found with ID: " + id);
        }
        cartRepository.deleteById(id);
    }
}
