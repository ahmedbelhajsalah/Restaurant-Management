package com.restaurant.restaurant_spring.entities;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    public CategoryDto getCategoryDto(){
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(id);
        categoryDto.setDescription(description);
        categoryDto.setName(name);
        categoryDto.setReturnedImg(img);
        return categoryDto;
    }
}
