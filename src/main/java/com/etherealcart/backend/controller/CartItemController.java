package com.etherealcart.backend.controller;


import com.etherealcart.backend.model.CartItem;
import com.etherealcart.backend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {
    @Autowired
    private CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<CartItem> createCartItem(@RequestParam Long cartId, @RequestParam Long productId, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartItemService.createCartItem(cartId, productId, quantity));
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<List<CartItem>> getCartItemsByCartId(@PathVariable Long cartId) {
        return ResponseEntity.ok(cartItemService.getCartItemsByCartId(cartId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartItem> getCartItemById(@PathVariable Long id) {
        Optional<CartItem> cartItem = cartItemService.getCartItemById(id);
        return cartItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItem> updateCartItem(@PathVariable Long id, @RequestParam Integer quantity) {
        return ResponseEntity.ok(cartItemService.updateCartItem(id, quantity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long id) {
        cartItemService.deleteCartItem(id);
        return ResponseEntity.noContent().build();
    }
}
