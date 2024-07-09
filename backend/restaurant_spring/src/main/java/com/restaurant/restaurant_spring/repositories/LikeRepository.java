package com.restaurant.restaurant_spring.repositories;

import com.restaurant.restaurant_spring.entities.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<UserLike, Long> {

}
