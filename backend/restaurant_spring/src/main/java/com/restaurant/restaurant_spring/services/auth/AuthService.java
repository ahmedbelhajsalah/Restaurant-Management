package com.restaurant.restaurant_spring.services.auth;

import com.restaurant.restaurant_spring.dto.SignupRequest;
import com.restaurant.restaurant_spring.dto.UserDto;

public interface AuthService {
    UserDto createUser(SignupRequest signupRequest);
}
