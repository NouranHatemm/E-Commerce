package com.example.ecommerce.services.admin.category;

import com.example.ecommerce.dto.CategoryDto;
import com.example.ecommerce.entities.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDto categoryDto);
    List<Category> getAllCategories();
}
