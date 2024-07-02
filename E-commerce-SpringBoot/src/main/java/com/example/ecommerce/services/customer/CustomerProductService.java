package com.example.ecommerce.services.customer;

import com.example.ecommerce.dto.ProductDetailsDto;
import com.example.ecommerce.dto.ProductDto;

import java.util.List;

public interface CustomerProductService {

    List<ProductDto> getAllProducts();
    ProductDetailsDto getProductDetailsById(Long productId);

    List<ProductDto> searchProductsByName(String name);
}
