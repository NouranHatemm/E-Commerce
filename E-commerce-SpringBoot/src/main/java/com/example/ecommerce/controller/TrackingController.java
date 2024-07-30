package com.example.ecommerce.controller;


import com.example.ecommerce.services.customer.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TrackingController {

    private final CartService cartService;

//    @GetMapping("/order/{trackingId}")
//    public ResponseEntity<OrderDto> searchOrderByTrackingId(@PathVariable UUID trackingId){
//        OrderDto orderDto = cartService.searchOrderByTrackingId(trackingId);
//        if(orderDto == null) return ResponseEntity.notFound().build();
//        return ResponseEntity.ok(orderDto);
//    }
}
