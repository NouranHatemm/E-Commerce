package com.example.ecommerce.services.admin.adminOrder;

import com.example.ecommerce.dto.OrderDto;

import java.util.List;

public interface AdminOrderService {

    List<OrderDto> getPlacedOrders();


}
