package com.example.ecommerce.services.admin.adminOrder;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminOrderServiceImpl implements AdminOrderService {

    public final OrderRepository orderRepository;

    public List<OrderDto> getPlacedOrders(){
        List<Order> orderList = orderRepository.
                findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped, OrderStatus.Delivered));

        return orderList.stream().map(Order::getOrderDto).collect(Collectors.toList());
    }


    public OrderDto changeOrderStatus(Long orderId, String status){
        Optional<Order> Optionalorder = orderRepository.findById(orderId);
        if(Optionalorder.isPresent()){
            Order order = Optionalorder.get();

            if(Objects.equals(status, "Shipped")){
                order.setOrderStatus(OrderStatus.Shipped);
            } else if (Objects.equals(status, "Delivered")) {
                order.setOrderStatus(OrderStatus.Delivered);

            }
            return orderRepository.save(order).getOrderDto();
        }
        return null;
    }

}
