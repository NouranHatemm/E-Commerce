package com.example.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private Long price;
    private byte[] byteImg;
    private Long categoryId;
    private Long categoryName;
    private MultipartFile img;

}
