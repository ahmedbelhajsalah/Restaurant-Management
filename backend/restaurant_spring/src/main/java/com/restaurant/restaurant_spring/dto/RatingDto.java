package com.restaurant.restaurant_spring.dto;

import lombok.Data;

@Data
public class RatingDto {
    private Long userId;
    private Long productId;
    private int rating;
}
