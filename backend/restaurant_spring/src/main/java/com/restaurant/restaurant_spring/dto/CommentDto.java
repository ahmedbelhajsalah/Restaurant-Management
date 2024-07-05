package com.restaurant.restaurant_spring.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Long id;
    private String content;
    private int likes;

    private Long user_id;
    private Long product_id;
    private int likeCount;

}
