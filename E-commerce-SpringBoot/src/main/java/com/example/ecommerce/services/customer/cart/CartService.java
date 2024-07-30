package com.example.ecommerce.services.customer.cart;

import com.example.ecommerce.dto.AddProductInCartDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.PlaceOrderDto;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    public OrderDto getCartByUserId(Long userId);

    OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto);

    OrderDto placeOrder(PlaceOrderDto placeOrderDto);

    List<OrderDto> getMyPlacedOrders(Long userId);

    OrderDto searchOrderByTrackingId(Long trackingId);
}
