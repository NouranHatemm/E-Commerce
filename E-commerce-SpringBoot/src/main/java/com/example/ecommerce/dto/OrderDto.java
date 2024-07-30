package com.example.ecommerce.dto;

import com.example.ecommerce.enums.OrderStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class OrderDto {

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long trackingId;
    private Long Id;
    private String userName;


    private List<CartItemDto> cartItems;

}
