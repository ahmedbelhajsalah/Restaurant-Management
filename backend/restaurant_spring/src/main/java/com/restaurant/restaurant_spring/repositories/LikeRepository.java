package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<UserLike, Long> {

    @Query("SELECT COUNT(ul) FROM UserLike ul WHERE ul.comment.id = :commentId")
    long countLikesByCommentId(@Param("commentId") Long commentId);

    @Query("SELECT COUNT(ul) FROM UserLike ul WHERE ul.reply.id = :replyId")
    long countLikesByReplyId(Long replyId);

    boolean existsByCommentIdAndUserId(Long commentId, Long userId);

    void deleteByCommentIdAndUserId(Long commentId, Long userId);

    boolean existsByReplyIdAndUserId(Long replyId, Long userId);

    void deleteByReplyIdAndUserId(Long replyId, Long userId);
}
