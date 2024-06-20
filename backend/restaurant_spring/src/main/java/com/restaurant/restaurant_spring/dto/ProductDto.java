package com.restaurant.restaurant_spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.restaurant_spring.entities.Category;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductDto {


    private Long id;

    private String name;

    private  String price;

    private String description;

    private byte[] returnedImg;

    private MultipartFile img;

    private Long categoryId;

    private String categoryName;

}
