package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.dto.ReplyDto;
import com.restaurant.restaurant_spring.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findAllByParentCommentId(Long commentId);
}
