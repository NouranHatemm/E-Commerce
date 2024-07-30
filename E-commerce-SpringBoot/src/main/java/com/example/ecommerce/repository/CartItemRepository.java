package com.example.ecommerce.repository;


import com.example.ecommerce.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductIdAndOrderIdAndUserId(Long productId, Long orderId, Long userId);

    Optional<CartItem> findByProductIdAndUserId(Long productId, Long userId);

    List<CartItem> findByUserId(Long userId);

    List<CartItem> getCartByUserId(Long userId);

}
