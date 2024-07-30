package com.example.ecommerce.entities;


import com.example.ecommerce.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@Table(name = "cartItem")
@NoArgsConstructor
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long price;
    private Long quantity;


    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    public CartItem(String itemId, String dummyItem, BigDecimal bigDecimal, int quantity) {

    }


    public CartItemDto getCartDto() {
        CartItemDto cartItemDto = new CartItemDto();
        cartItemDto.setQuantity(quantity);
        cartItemDto.setPrice(price);
        cartItemDto.setId(id);
        cartItemDto.setProductId(product.getId());
        cartItemDto.setProductName(product.getProductName());
        cartItemDto.setUserId(user.getId());
        cartItemDto.setReturnImg(product.getImg());


        return cartItemDto;

    }

}
