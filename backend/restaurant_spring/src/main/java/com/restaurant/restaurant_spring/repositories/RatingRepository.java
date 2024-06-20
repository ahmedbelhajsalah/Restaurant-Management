package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByProductId(Long productId);
}
