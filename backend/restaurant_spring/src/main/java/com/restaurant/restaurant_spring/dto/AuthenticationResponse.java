package com.restaurant.restaurant_spring.dto;

import com.restaurant.restaurant_spring.enums.UserRole;
import lombok.Data;

@Data

public class AuthenticationResponse {

    private String jwt;
    private UserRole userRole;
    private Long userId;
    private String name;
}
