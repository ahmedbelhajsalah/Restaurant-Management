package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.dto.RatingDto;
import com.restaurant.restaurant_spring.entities.Rating;
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

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categoryDto = customerService.getAllCategories();
        if(categoryDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categoryDto);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId) throws IOException {
        List<ProductDto> createdProductDto = customerService.getAllProductsByCategory(categoryId);
        if(createdProductDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
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
}
