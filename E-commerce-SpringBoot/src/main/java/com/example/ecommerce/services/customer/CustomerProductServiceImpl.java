package com.example.ecommerce.services.customer;


import com.example.ecommerce.dto.ProductDetailsDto;
import com.example.ecommerce.dto.ProductDto;
import com.example.ecommerce.entities.FAQ;
import com.example.ecommerce.entities.Product;
import com.example.ecommerce.repository.FAQRepository;
import com.example.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerProductServiceImpl implements CustomerProductService {

    private final ProductRepository productRepository;
    private final FAQRepository faqRepository;

    public List<ProductDto> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }




    public ProductDetailsDto getProductDetailsById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            List<FAQ> faqList = faqRepository.findAllByProductId(productId);

            ProductDetailsDto productDetailsDto = new ProductDetailsDto();

            productDetailsDto.setProductDto(optionalProduct.get().getDto());
            productDetailsDto.setFaqDtoList(faqList.stream().map(FAQ::getFAQDto).collect(Collectors.toList()));

            return productDetailsDto;
        }
        return null;
    }




    public List<ProductDto> searchProductsByName(String name){
        List<Product> products = productRepository.findAllByProductNameContaining(name);
        return products.stream().map(Product::getDto).collect(Collectors.toList());
    }



//    @Override
//    public List<ProductDto> getAllProducts() {
//        return List.of();
//    }


}
