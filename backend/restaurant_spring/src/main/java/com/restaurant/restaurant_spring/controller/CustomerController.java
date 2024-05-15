package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.services.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
