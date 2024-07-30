package com.example.ecommerce.services.customer.cart;

import com.example.ecommerce.dto.AddProductInCartDto;
import com.example.ecommerce.dto.CartItemDto;
import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.PlaceOrderDto;
import com.example.ecommerce.entities.CartItem;
import com.example.ecommerce.entities.Order;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.entities.User;
import com.example.ecommerce.enums.OrderStatus;
import com.example.ecommerce.repository.CartItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;


    public ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto) {

        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(
                addProductInCartDto.getUserId(), OrderStatus.Pending);

        if (activeOrder == null) {

            activeOrder = new Order();
            activeOrder.setOrderStatus(OrderStatus.Pending);
            activeOrder.setUser(userRepository.getById(addProductInCartDto.getUserId()));
            activeOrder.setTotalAmount(0L);
            activeOrder = orderRepository.save(activeOrder);
        }

        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(),
                addProductInCartDto.getUserId());

        if (optionalCartItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        } else {

            Optional<Product> optionalProduct = productRepository.findById(addProductInCartDto.getProductId());
            Optional<User> optionalUser = userRepository.findById(addProductInCartDto.getUserId());

            if (optionalProduct.isPresent() && optionalUser.isPresent()) {
                Product product = optionalProduct.get();
                User user = optionalUser.get();


                CartItem cart = new CartItem();
                cart.setProduct(product);
                cart.setPrice(product.getPrice());
                cart.setQuantity(1L);
                cart.setUser(user);
                cart.setOrder(activeOrder);

                CartItem updatedCart = cartItemRepository.save(cart);

                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + cart.getPrice());
//activeOrder.setAmount(activeOrder.getAmount() + cart.getPrice());
                activeOrder.getCartItems().add(cart);

                orderRepository.save(activeOrder);


                return ResponseEntity.status(HttpStatus.CREATED).body(updatedCart.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User or Product not found");
            }
        }
    }


    public OrderDto getCartByUserId(Long userId) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(userId, OrderStatus.Pending);

        if (activeOrder == null) {

            activeOrder = new Order();
            activeOrder.setOrderStatus(OrderStatus.Pending);
            activeOrder.setTotalAmount(0L);
            activeOrder = orderRepository.save(activeOrder);
        }

        List<CartItemDto> cartItemsDtoList = activeOrder.getCartItems().stream()
                .map(CartItem::getCartDto)
                .collect(Collectors.toList());

        OrderDto orderDto = new OrderDto();
        orderDto.setAmount(activeOrder.getTotalAmount());
        orderDto.setId(activeOrder.getId());
        orderDto.setOrderStatus(activeOrder.getOrderStatus());
        orderDto.setTotalAmount(activeOrder.getTotalAmount());
        orderDto.setCartItems(cartItemsDtoList);

        return orderDto;
    }


    public OrderDto increaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            Product product = productRepository.findById(addProductInCartDto.getProductId()).orElse(null);

            if (product != null) {
                if (activeOrder.getAmount() == null) {
                    activeOrder.setAmount(0L);
                }
                if (activeOrder.getTotalAmount() == null) {
                    activeOrder.setTotalAmount(0L);
                }

                activeOrder.setAmount(activeOrder.getAmount() + product.getPrice());
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() + product.getPrice());

                cartItem.setQuantity(cartItem.getQuantity() + 1);

                cartItemRepository.save(cartItem);
                orderRepository.save(activeOrder);
                return activeOrder.getOrderDto();
            }
        }
        return null;
    }

    public OrderDto decreaseProductQuantity(AddProductInCartDto addProductInCartDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(addProductInCartDto.getUserId(), OrderStatus.Pending);
        Optional<CartItem> optionalCartItem = cartItemRepository.findByProductIdAndOrderIdAndUserId(
                addProductInCartDto.getProductId(), activeOrder.getId(), addProductInCartDto.getUserId()
        );

        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            Product product = productRepository.findById(addProductInCartDto.getProductId()).orElse(null);

            if (product != null) {
                if (activeOrder.getAmount() == null) {
                    activeOrder.setAmount(0L);
                }
                if (activeOrder.getTotalAmount() == null) {
                    activeOrder.setTotalAmount(0L);
                }

                activeOrder.setAmount(activeOrder.getAmount() - product.getPrice());
                activeOrder.setTotalAmount(activeOrder.getTotalAmount() - product.getPrice());

                cartItem.setQuantity(cartItem.getQuantity() - 1);

                cartItemRepository.save(cartItem);
                orderRepository.save(activeOrder);
                return activeOrder.getOrderDto();
            }
        }
        return null;
    }

    public OrderDto placeOrder(PlaceOrderDto placeOrderDto) {
        Order activeOrder = orderRepository.findByUserIdAndOrderStatus(placeOrderDto.getUserId(), OrderStatus.Pending);
        Optional<User> optionalUser = userRepository.findById(placeOrderDto.getUserId());
        if (optionalUser.isPresent()) {

            if (activeOrder == null) {

                activeOrder = new Order();
                activeOrder.setOrderStatus(OrderStatus.Pending);
                activeOrder.setTotalAmount(0L);
                activeOrder = orderRepository.save(activeOrder);
            }
            activeOrder.setOrderDescription(placeOrderDto.getOrderDescription());
            activeOrder.setDate(new Date());
            activeOrder.setAddress(placeOrderDto.getAddress());
            activeOrder.setOrderStatus(OrderStatus.Placed);
            activeOrder.setTrackingId(activeOrder.getTrackingId());

            orderRepository.save(activeOrder);

//            Order order = new Order();
//           order.setAmount(0L);
//            order.setUser(optionalUser.get());
//            order.setOrderStatus(OrderStatus.Pending);
//            order.setTotalAmount(0L);
//            Long lastTrackingId = orderRepository.findMaxTrackingId();
//            System.out.print(lastTrackingId);
//            // Increment the trackingId
//            order.setTrackingId((lastTrackingId != null ? lastTrackingId : 0) + 1);
//            orderRepository.save(order);
//
            return activeOrder.getOrderDto();

        }
        return null;

    }

    @Override
    public List<OrderDto> getMyPlacedOrders(Long userId) {
        return orderRepository.findAllByOrderStatusIn(List.of(OrderStatus.Placed, OrderStatus.Shipped,
                OrderStatus.Delivered)).stream().map(Order::getOrderDto).collect(Collectors.toList());
    }

    @Override
    public OrderDto searchOrderByTrackingId(Long trackingId) {
        return null;
    }


}


