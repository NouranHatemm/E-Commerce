package com.example.ecommerce.services.admin.adminproduct;

import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entities.Category;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminProductServiceImpl implements AdminProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto productDto) throws IOException, EntityNotFoundException {
        Product product = new Product();
        product.setProductName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        if (productDto.getImg() != null) {
            product.setImg(productDto.getImg().getBytes());
        }

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow();


        product.setCategory(category);
        return productRepository.save(product).getDto();


    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByName(String name) {
        List<Product> products = productRepository.findAllByProductNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }


    @Override
    public boolean deleteProduct(Long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            return optionalProduct.get().getDto();
        } else {
            return null;
        }
    }

    @Override
    @Transactional
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        Optional<Category> optionalCategory = categoryRepository.findById(productDto.getCategoryId());
        if (optionalProduct.isPresent() && optionalCategory.isPresent()) {
            Product product = optionalProduct.get();

            product.setProductName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setCategory(optionalCategory.get());

            if (productDto.getImg() != null) {
                product.setImg(productDto.getImg().getBytes());
            }

            return productRepository.save(product).getDto();
        } else {
            throw new EntityNotFoundException("Product or Category not found");
        }
    }
}
