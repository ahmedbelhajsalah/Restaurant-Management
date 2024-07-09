package com.restaurant.restaurant_spring.dto;

import com.restaurant.restaurant_spring.entities.Reply;
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
