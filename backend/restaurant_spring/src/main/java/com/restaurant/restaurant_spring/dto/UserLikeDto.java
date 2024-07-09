package com.restaurant.restaurant_spring.dto;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserLikeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long comment_id;

    private Long user_id;

    private Long reply_id;
}
