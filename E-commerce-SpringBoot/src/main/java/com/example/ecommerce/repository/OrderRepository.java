package com.example.ecommerce.repository;

import com.example.ecommerce.entities.Order;
import com.example.ecommerce.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findByUserIdAndOrderStatus(Long userId, OrderStatus orderStatus);

    List<Order> findAllByOrderStatusIn(List<OrderStatus> orderStatusList);

    Order findByUserId(Long userId);

    Optional<Order> findByTrackingId(Long trackingId);

    @Query("SELECT MAX(e.trackingId) FROM Order e")
    Long findMaxTrackingId();
//List <Order> findAllByUserIdAndOrderStatusIn(Long userId, OrderStatus orderStatus);


}
