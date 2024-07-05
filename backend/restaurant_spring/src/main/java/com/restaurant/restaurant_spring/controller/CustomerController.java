package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.*;
import com.restaurant.restaurant_spring.entities.Comment;
import com.restaurant.restaurant_spring.entities.Rating;
import com.restaurant.restaurant_spring.repositories.CommentRepository;
import com.restaurant.restaurant_spring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    public final CustomerService customerService;
    private final CommentRepository commentRepository;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDto = customerService.getAllCategories();
        if(categoryDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId) throws IOException {
        List<ProductDto> createdProductDto = customerService.getAllProductsByCategory(categoryId);
        if(createdProductDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(createdProductDto);
    }

    @PostMapping("/product/rate")
    public ResponseEntity<?> postRating(@RequestBody RatingDto ratingDto) throws IOException {
        Rating createdRating = customerService.addRating(ratingDto.getUserId(), ratingDto.getProductId(), ratingDto.getRating());
        if(createdRating == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
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
        if(productDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(productDto);
    }

    @PostMapping("/product/createComment")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) throws IOException {
        CommentDto createdComment = customerService.saveComment(commentDto);
        if (createdComment == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdComment);
    }

    @GetMapping("/product/getAllCommentsByProductId/{productId}")
    public ResponseEntity<?> getAllCommentsByProductId(@PathVariable Long productId){
        List<CommentDto> getCommentDto = customerService.getAllCommentsByProductId(productId);
        if(getCommentDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(getCommentDto);
    }

    @PostMapping("/comment/reply")
    public ResponseEntity<?> postReply(@RequestBody ReplyDto replyDto){
        ReplyDto createdReplyDto = customerService.postReply(replyDto);
        if (createdReplyDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReplyDto);
    }
}
