package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.entities.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
