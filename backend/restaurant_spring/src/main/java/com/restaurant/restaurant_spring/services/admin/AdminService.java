package com.restaurant.restaurant_spring.services.admin;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

    List<CategoryDto> getAllCategories();

    List<CategoryDto> getAllCategoriesByTitle(String title);

    ProductDto postProduct(Long productId, ProductDto productDto) throws IOException;

    List<ProductDto> getAllProductsByCategory(Long productId);

    List<ProductDto> getAllProductsByCategoryIdAndTitle(Long categoryId, String title);

    void deleteProduct(Long categoryId);

    ProductDto getProductById(Long productId);

    ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException;

    void deleteCategory(Long categoryId);

    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) throws IOException;

    CategoryDto getCategoryById(Long categoryId);
}
