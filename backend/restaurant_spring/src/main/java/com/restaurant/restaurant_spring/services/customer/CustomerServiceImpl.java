package com.restaurant.restaurant_spring.services.customer;

import com.restaurant.restaurant_spring.dto.*;
import com.restaurant.restaurant_spring.entities.*;
import com.restaurant.restaurant_spring.repositories.*;
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
    private final CommentRepository commentRepository;
    private final ReplyRepository replyRepository;
    private final LikeRepository likeRepository;

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

    @Override
    public CommentDto saveComment(CommentDto commentDto) throws IOException {
        Optional<Product> productOpt = productRepository.findById(commentDto.getProduct_id());
        Optional<User> userOpt = userRepository.findById(commentDto.getUser_id());

        if (productOpt.isPresent() && userOpt.isPresent()) {
            Product product = productOpt.get();
            User user = userOpt.get();

            Comment comment = new Comment();
            comment.setContent(commentDto.getContent());
            comment.setUser(user);
            comment.setProduct(product);


            Comment createdComment = commentRepository.save(comment);

            CommentDto createdCommentDto = new CommentDto();
            createdCommentDto.setId(createdComment.getId());
            createdCommentDto.setContent(createdComment.getContent());
            createdCommentDto.setUser_id(createdComment.getUser().getId());
            createdCommentDto.setProduct_id(createdComment.getProduct().getId());
            return createdCommentDto;
        }
        return null;
    }

    @Override
    public List<CommentDto> getAllCommentsByProductId(Long productId) {
        return commentRepository.findAllByProductId(productId).stream().map(Comment::getCommentDto).collect(Collectors.toList());
    }

    @Override
    public ReplyDto postReply(ReplyDto replyDto) {
        Optional<User> optionalUserDto = userRepository.findById(replyDto.getUser_id());
        Optional<Comment> optionalComment = commentRepository.findById(replyDto.getComment_id());
        if(optionalUserDto.isPresent() && optionalComment.isPresent()){
            User user = optionalUserDto.get();
            Comment comment = optionalComment.get();
            Reply reply = new Reply();
            reply.setContent(replyDto.getContent());
            reply.setUser(user);
            reply.setParentComment(comment);
            Reply createdReplay = replyRepository.save(reply);
            ReplyDto createdReplyDto = new ReplyDto();
            createdReplyDto.setContent(createdReplay.getContent());
            createdReplyDto.setId(createdReplay.getId());
            createdReplyDto.setUser_id(user.getId());
            createdReplyDto.setComment_id(comment.getId());
            return createdReplyDto;
        }
        return null;
    }

    @Override
    public UserLikeDto LikeComment(Long commentId, Long userId) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalComment.isPresent() && optionalUser.isPresent()){
            Comment comment = optionalComment.get();
            User user = optionalUser.get();
            UserLike userLike = new UserLike();
            userLike.setComment(comment);
            userLike.setUser(user);
            UserLike createdUserLike = likeRepository.save(userLike);

            UserLikeDto userLikeDto = new UserLikeDto();
            userLikeDto.setId(createdUserLike.getId());
            userLikeDto.setComment_id(comment.getId());
            userLikeDto.setUser_id(user.getId());
            return userLikeDto;
        }
        return null;
    }

    @Override
    public UserLikeDto LikeReply(Long replyId, Long userId) {
        Optional<Reply> optionalReply = replyRepository.findById(replyId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalReply.isPresent() && optionalUser.isPresent()){
            Reply reply = optionalReply.get();
            User user = optionalUser.get();
            UserLike userLike = new UserLike();
            userLike.setReply(reply);
            userLike.setUser(user);
            UserLike createdUserLike = likeRepository.save(userLike);

            UserLikeDto userLikeDto = new UserLikeDto();
            userLikeDto.setId(createdUserLike.getId());
            userLikeDto.setComment_id(reply.getId());
            userLikeDto.setUser_id(user.getId());
            return userLikeDto;
        }
        return null;
    }

    @Override
    public List<ReplyDto> getAllReplyByCommentId(Long commentId) {
        return replyRepository.findAllByParentCommentId(commentId).stream().map(Reply::getReplyDto).collect(Collectors.toList());
    }

}
