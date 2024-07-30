package com.example.ecommerce.controller.customer;

import com.example.ecommerce.dto.ProductDetailsDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.services.customer.CustomerProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class CustomerProductController {

    private final CustomerProductService customerProductService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> productDtos = customerProductService.getAllProducts();
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/search/{name}")
    public ResponseEntity<List<ProductDto>> getProductsByName(@PathVariable String name) {
        List<ProductDto> productDtos = customerProductService.searchProductsByName(name);
        return ResponseEntity.ok(productDtos);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<ProductDetailsDto> getProductDetailsById(@PathVariable Long productId) {
        ProductDetailsDto productDetailsDto = customerProductService.getProductDetailsById(productId);
        if (productDetailsDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetailsDto);
    }
}
