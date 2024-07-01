package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.entities.Rating;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CategoryDto> getAllCategories();

    List<ProductDto> getAllProductsByCategory(Long categoryId);

    Rating addRating(Long userId, Long productId, int ratingValue) throws IOException;

    double getAverageRating(Long productId);

    ProductDto getProductById(Long productId);
}
