package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.*;
import com.restaurant.restaurant_spring.entities.Comment;
import com.restaurant.restaurant_spring.entities.Rating;
import com.restaurant.restaurant_spring.entities.UserLike;
import com.restaurant.restaurant_spring.repositories.CommentRepository;
import com.restaurant.restaurant_spring.repositories.LikeRepository;
import com.restaurant.restaurant_spring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> categoryDto = customerService.getAllCategories();
        if (categoryDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId) throws IOException {
        List<ProductDto> createdProductDto = customerService.getAllProductsByCategory(categoryId);
        if (createdProductDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(createdProductDto);
    }

    @PostMapping("/product/rate")
    public ResponseEntity<?> postRating(@RequestBody RatingDto ratingDto) throws IOException {
        Rating createdRating = customerService.addRating(ratingDto.getUserId(), ratingDto.getProductId(), ratingDto.getRating());
        if (createdRating == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRating);
    }

    @GetMapping("/product/averageRating/{productId}")
    public ResponseEntity<Double> getAverageRating(@PathVariable Long productId) {
        double averageRating = customerService.getAverageRating(productId);
        return ResponseEntity.ok(averageRating);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId) throws IOException {
        ProductDto productDto = customerService.getProductById(productId);
        if (productDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/product/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) throws IOException {
        CommentDto createdComment = customerService.saveComment(commentDto);
        if (createdComment == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/product/getAllCommentsByProductId/{productId}")
    public ResponseEntity<?> getAllCommentsByProductId(@PathVariable Long productId) {
        List<CommentDto> getCommentDto = customerService.getAllCommentsByProductId(productId);
        if (getCommentDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(getCommentDto);
    }

    @PostMapping("/comment/reply")
    public ResponseEntity<?> postReply(@RequestBody ReplyDto replyDto) {
        ReplyDto createdReplyDto = customerService.postReply(replyDto);
        if (createdReplyDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReplyDto);
    }

    @PostMapping("/comment/like")
    public ResponseEntity<?> likeComment(@RequestBody UserLikeDto userLikeDto) {
        UserLikeDto userLike = customerService.LikeComment(userLikeDto.getComment_id(), userLikeDto.getUser_id());
        if (userLike == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(userLike);
    }

    @PostMapping("/reply/like")
    public ResponseEntity<?> replyLike(@RequestBody UserLikeDto userLikeDto) {
        UserLikeDto userLike = customerService.LikeReply(userLikeDto.getReply_id(), userLikeDto.getUser_id());
        if (userLike == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(userLike);
    }

    @GetMapping("/reply/getAllRepliesPerComment")
    public ResponseEntity<?> getAllRepliesPerComment(@RequestParam Long comment_id){
        List<ReplyDto> getReplyDto = customerService.getAllReplyByCommentId(comment_id);
        if (getReplyDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(getReplyDto);
    }

    @DeleteMapping("/deleteComments/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long comment_id) throws IOException {
        customerService.deleteComment(comment_id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteReply/{reply_id}")
    public ResponseEntity<Void> deleteReply(@PathVariable Long reply_id) throws IOException {
        customerService.deleteReply(reply_id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/countCommentLike/{commentId}")
    public ResponseEntity<?> countCommentLike(@PathVariable Long commentId){
        long likeCount = likeRepository.countLikesByCommentId(commentId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/countReplyLike/{replyId}")
    public ResponseEntity<?> countReplyLike(@PathVariable Long replyId){
        long likeCount = likeRepository.countLikesByReplyId(replyId);
        return ResponseEntity.ok(likeCount);
    }

    @GetMapping("/{userId}/name")
    public ResponseEntity<Map<String, String>> getUserNameById(@PathVariable Long userId) {
        String userName = customerService.getUserNameById(userId);
        if (userName != null) {
            Map<String, String> response = new HashMap<>();
            response.put("userName", userName);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/isCommentLiked/{commentId}/{userId}")
    public ResponseEntity<Boolean> isCommentLiked(@PathVariable Long commentId, @PathVariable Long userId) {
        boolean isLiked = customerService.isCommentLikedByUser(commentId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @DeleteMapping("/unlikeComment/{commentId}/{userId}")
    public ResponseEntity<Void> unlikeComment(@PathVariable Long commentId, @PathVariable Long userId) {
        customerService.unlikeComment(commentId, userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/isReplyLiked/{replyId}/{userId}")
    public ResponseEntity<Boolean> isReplyLiked(@PathVariable Long replyId, @PathVariable Long userId) {
        boolean isLiked = customerService.isReplyLikedByUser(replyId, userId);
        return ResponseEntity.ok(isLiked);
    }

    @DeleteMapping("/unlikeReply/{replyId}/{userId}")
    public ResponseEntity<Void> unlikeReply(@PathVariable Long replyId, @PathVariable Long userId) {
        customerService.unlikeReply(replyId, userId);
        return ResponseEntity.noContent().build();
    }
}
