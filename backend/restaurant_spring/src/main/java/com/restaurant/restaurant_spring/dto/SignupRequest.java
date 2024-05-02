package com.restaurant.restaurant_spring.dto;

import com.restaurant.restaurant_spring.enums.UserRole;
import lombok.Data;

@Data
public class SignupRequest {

    private String name;

    private String email;

    private String password;

    private UserRole userRole;
}
