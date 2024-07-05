package com.restaurant.restaurant_spring.dto;

import lombok.Data;

@Data
public class ReplyDto {

    private Long id;
    private String content;
    private Long user_id;
    private Long comment_id;
    private int likes;
    private int likeCount;

}
