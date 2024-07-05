package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.CommentDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.dto.ReplyDto;
import com.restaurant.restaurant_spring.entities.Comment;
import com.restaurant.restaurant_spring.entities.Rating;

import java.io.IOException;
import java.util.List;

public interface CustomerService {
    List<CategoryDto> getAllCategories();

    List<ProductDto> getAllProductsByCategory(Long categoryId);

    Rating addRating(Long userId, Long productId, int ratingValue) throws IOException;

    double getAverageRating(Long productId);

    ProductDto getProductById(Long productId);

    CommentDto saveComment(CommentDto commentDto) throws IOException;

    List<CommentDto> getAllCommentsByProductId(Long productId);

    ReplyDto postReply(ReplyDto replyDto);
}
