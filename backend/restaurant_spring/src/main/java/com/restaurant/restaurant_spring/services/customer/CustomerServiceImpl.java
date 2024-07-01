package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.CategoryDto;
import com.restaurant.restaurant_spring.dto.ProductDto;
import com.restaurant.restaurant_spring.entities.Category;
import com.restaurant.restaurant_spring.entities.Product;
import com.restaurant.restaurant_spring.entities.Rating;
import com.restaurant.restaurant_spring.entities.User;
import com.restaurant.restaurant_spring.repositories.CategoryRepository;
import com.restaurant.restaurant_spring.repositories.ProductRepository;
import com.restaurant.restaurant_spring.repositories.RatingRepository;
import com.restaurant.restaurant_spring.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    public final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    public final RatingRepository ratingRepository;
    public final UserRepository userRepository;

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream().map(Category::getCategoryDto).collect(Collectors.toList());
    }

    @Override
    public List<ProductDto> getAllProductsByCategory(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId).stream().map(Product::getProductDto).collect(Collectors.toList());
    }

    @Override
    public Rating addRating(Long userId, Long productId, int ratingValue) throws IOException {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<User> userOpt = userRepository.findById(userId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();
            User user = userOpt.get();
            Rating rating = new Rating();
            rating.setUser(user);
            rating.setProduct(product);
            rating.setRating(ratingValue);
            return ratingRepository.save(rating);
        }else {
            throw new IllegalArgumentException("Product with id: " + productId + " not found");
        }

    }

    @Override
    public double getAverageRating(Long productId) {
        List<Rating> ratings = ratingRepository.findByProductId(productId);
        double average = ratings.stream().mapToInt(Rating::getRating).average().orElse(0.0);
        return Math.round(average * 2) / 2.0; // Round to the nearest 0.5
    }

    @Override
    public ProductDto getProductById(Long productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.map(Product::getProductDto).orElse(null);
    }

}
