package com.restaurant.restaurant_spring.services.admin;

import com.restaurant.restaurant_spring.dto.CategoryDto;

import java.io.IOException;
import java.util.List;

public interface AdminService {
    CategoryDto postCategory(CategoryDto categoryDto) throws IOException;

    List<CategoryDto> getAllCategories();
}
