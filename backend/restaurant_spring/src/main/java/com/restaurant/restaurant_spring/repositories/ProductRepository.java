package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCategoryId(Long categoryId);
    List<Product> findAllByCategoryIdAndNameContaining(Long categoryId, String title);

    ProductDto findProductById(Long productId);
}
