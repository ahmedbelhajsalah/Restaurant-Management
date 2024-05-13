package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.repositories.CategoryRepository;
import com.restaurant.restaurant_spring.services.admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/category")
    public ResponseEntity<CategoryDto> postCategory(@ModelAttribute CategoryDto categoryDto) throws IOException {
        CategoryDto createdCategoryDto = adminService.postCategory(categoryDto);
        if(createdCategoryDto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(createdCategoryDto);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> categories = adminService.getAllCategories();
        if(categories == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/categories/{title}")
    public ResponseEntity<List<CategoryDto>> getAllCategoriesByTitle(@PathVariable String title){
        List<CategoryDto> categories = adminService.getAllCategoriesByTitle(title);
        if(categories == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(categories);
    }

    // Product Operations
    @PostMapping("/product/{productId}")
    public ResponseEntity<?> postProduct(@PathVariable Long productId ,@ModelAttribute ProductDto productDto) throws IOException {
        ProductDto createdProductDto = adminService.postProduct(productId, productDto);
        if(createdProductDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }

    @GetMapping("/products/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable Long categoryId) throws IOException {
        List<ProductDto> createdProductDto = adminService.getAllProductsByCategory(categoryId);
        if(createdProductDto == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something Went Wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductDto);
    }
}
