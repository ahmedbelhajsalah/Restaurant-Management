package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.CategoryDto;

import java.util.List;

public interface CustomerService {
    List<CategoryDto> getAllCategories();
}
