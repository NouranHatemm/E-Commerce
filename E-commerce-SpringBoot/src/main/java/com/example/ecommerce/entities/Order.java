package com.example.ecommerce.entities;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Entity
@Setter
@Getter
@Table(name = "orders")
public class Order {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackingId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItem> cartItems;

    public OrderDto getOrderDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setOrderDescription(orderDescription);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setId(id);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setAddress(address);
        orderDto.setTotalAmount(totalAmount);
        orderDto.setTrackingId(trackingId);
        return orderDto;
    }


}
