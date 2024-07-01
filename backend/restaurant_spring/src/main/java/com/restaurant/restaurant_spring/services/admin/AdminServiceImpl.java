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
            List<byte[]> additionalImages = productDto.getAdditionalImages().stream()
                    .map(multipartFile -> {
                        try {
                            return multipartFile.getBytes();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
            product.setAdditionalImages(additionalImages);
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

    @Override
    public List<ProductDto> getAllProductsByCategoryIdAndTitle(Long categoryId, String title) {
        return productRepository.findAllByCategoryIdAndNameContaining(categoryId, title).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public void deleteProduct(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(productId);
        } else {
            throw new IllegalArgumentException("Product with id: " + productId + " not found");
        }
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getProductDto).orElse(null);
    }

    @Override
    public ProductDto updateProduct(Long productId, ProductDto productDto) throws IOException {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            product.setName(productDto.getName());
            product.setDescription(productDto.getDescription());
            product.setPrice(productDto.getPrice());
            product.setDetailedDescription(productDto.getDetailedDescription());
            if(productDto.getImg() != null){
                product.setImg(productDto.getImg().getBytes());
            }
            if(productDto.getAdditionalImages() != null){
                List<byte[]> additionalImages = productDto.getAdditionalImages().stream()
                        .map(multipartFile -> {
                            try {
                                return multipartFile.getBytes();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collect(Collectors.toList());
                product.setAdditionalImages(additionalImages);
            }
            Product updatedProduct = productRepository.save(product);
            ProductDto updatedProductDto = new ProductDto();
            updatedProductDto.setId(updatedProduct.getId());
            return updatedProductDto;
        }
        return null;
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            categoryRepository.deleteById(categoryId);
        } else {
            throw new IllegalArgumentException("Category with id: " + categoryId + " not found");
        }
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto) throws IOException {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        System.out.println("hereeeeeeeeeeeeeeeeeeeeeeeeee");
        if(optionalCategory.isPresent()){
            Category category = optionalCategory.get();
            category.setName(categoryDto.getName());
            category.setDescription(categoryDto.getDescription());
            if(optionalCategory.get().getImg() != null){
                category.setImg(categoryDto.getImg().getBytes());
                System.out.println(category);
            }
            Category updatedCategory = categoryRepository.save(category);
            CategoryDto updatedCategoryDto = new CategoryDto();
            updatedCategoryDto.setId(updatedCategory.getId());
            return updatedCategoryDto;
        }
        return null;
    }

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        return optionalCategory.map(Category::getCategoryDto).orElse(null);
    }
}
