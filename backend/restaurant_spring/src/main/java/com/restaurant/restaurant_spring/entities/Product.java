package com.restaurant.restaurant_spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.restaurant.restaurant_spring.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private  String price;

    private String description;

    @Lob
    @Column(columnDefinition = "longblob")
    private byte[] img;

    private String detailedDescription;

    @ElementCollection
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "image", columnDefinition = "longblob")
    private List<byte[]> additionalImages;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "category_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Category category;

    public ProductDto getProductDto() {
        ProductDto productDto = new ProductDto();
        productDto.setId(id);
        productDto.setName(name);
        productDto.setReturnedImg(img);
        productDto.setDescription(description);
        productDto.setDetailedDescription(detailedDescription);
        productDto.setReturnedAdditionalImages(additionalImages);
        productDto.setPrice(price);
        productDto.setCategoryId(category.getId());
        productDto.setCategoryName(category.getName());
        return productDto;
    }

    @OneToMany(mappedBy = "product")
    private List<Rating> ratings;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
}
