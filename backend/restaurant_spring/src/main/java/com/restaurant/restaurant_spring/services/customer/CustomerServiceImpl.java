package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.entities.Category;
import com.restaurant.restaurant_spring.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    public final CategoryRepository categoryRepository;
    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }
}
