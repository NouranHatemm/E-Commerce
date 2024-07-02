package com.example.ecommerce.entities;


import com.example.ecommerce.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Setter
@Getter
@Table(name = "cartItem")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private Long quantity;
    private Long price;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;


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
