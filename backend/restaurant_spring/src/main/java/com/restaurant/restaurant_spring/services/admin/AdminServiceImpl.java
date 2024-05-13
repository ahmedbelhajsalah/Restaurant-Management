package com.restaurant.restaurant_spring.services.admin;

import ch.qos.logback.core.joran.util.beans.BeanUtil;
import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.entities.Category;
import com.restaurant.restaurant_spring.entities.Product;
import com.restaurant.restaurant_spring.repositories.CategoryRepository;
import com.restaurant.restaurant_spring.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public CategoryDto postCategory(CategoryDto categoryDto) throws IOException {
        Category category = new Category();
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        category.setImg(categoryDto.getImg().getBytes());
        Category createdCategory = categoryRepository.save(category);
        CategoryDto createdCategoryDto = new CategoryDto();
        createdCategoryDto.setId(category.getId());
        return createdCategoryDto;
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAllCategoriesByTitle(String title) {
        return categoryRepository.findByNameContaining(title).stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public ProductDto postProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(productId);
        if(optionalCategory.isPresent()){
            Product product = new Product();
            BeanUtils.copyProperties(productDto,product);
            product.setImg(productDto.getImg().getBytes());
            product.setCategory(optionalCategory.get());
            Product createdProduct = productRepository.save(product);
            ProductDto createdProductDto = new ProductDto();
            createdProductDto.setId(createdProduct.getId());
            return createdProductDto;
        }
        return null;
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(Long productId) {
        return productRepository.findAllByCategoryId(productId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }


}
