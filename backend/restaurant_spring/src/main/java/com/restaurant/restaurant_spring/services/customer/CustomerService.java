package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.*;
import com.restaurant.restaurant_spring.entities.Comment;
import com.restaurant.restaurant_spring.entities.Rating;
import com.restaurant.restaurant_spring.entities.UserLike;

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

    UserLikeDto LikeComment(Long commentId, Long userId);

    UserLikeDto LikeReply(Long replyId, Long userId);

    List<ReplyDto> getAllReplyByCommentId(Long commentId);
}
