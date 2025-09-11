package com.etherealcart.backend.service;


import com.etherealcart.backend.model.Cart;
import com.etherealcart.backend.model.CartItem;
import com.etherealcart.backend.model.Product;
import com.etherealcart.backend.repository.CartItemRepository;
import com.etherealcart.backend.repository.CartRepository;
import com.etherealcart.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;

    public CartItem createCartItem(Long cartId, Long productId, Integer quantity) {
        Optional<Cart> cart = cartRepository.findById(cartId);
        Optional<Product> product = productRepository.findById(productId);
        if (cart.isEmpty()) {
            throw new IllegalArgumentException("Cart not found with ID: " + cartId);
        }
        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart.get());
        cartItem.setProduct(product.get());
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemsByCartId(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    public Optional<CartItem> getCartItemById(Long id) {
        return cartItemRepository.findById(id);
    }

    public CartItem updateCartItem(Long id, Integer quantity) {
        Optional<CartItem> existingItem = cartItemRepository.findById(id);
        if (existingItem.isEmpty()) {
            throw new IllegalArgumentException("CartItem not found with ID: " + id);
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        CartItem cartItem = existingItem.get();
        cartItem.setQuantity(quantity);
        return cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Long id) {
        if (!cartItemRepository.existsById(id)) {
            throw new IllegalArgumentException("CartItem not found with ID: " + id);
        }
        cartItemRepository.deleteById(id);
    }
}
