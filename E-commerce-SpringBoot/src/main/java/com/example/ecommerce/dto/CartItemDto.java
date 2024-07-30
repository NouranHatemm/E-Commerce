package com.example.ecommerce.dto;

import lombok.Data;

@Data
public class CartItemDto {
    private Long id;
    private Long productId;
    private Long quantity;
    private Long price;
    private String productName;
    private Long orderId;
    private byte[] returnImg;
    private Long userId;


    public void setUserId(long id) {
    }
}
