package com.restaurant.restaurant_spring.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductDto {


    private Long id;

    private String name;

    private  String price;

    private String description;

    private String detailedDescription;

    private byte[] returnedImg;

    private List<MultipartFile> additionalImages;

    private List<byte[]> returnedAdditionalImages;

    private MultipartFile img;

    private Long categoryId;

    private String categoryName;

}
