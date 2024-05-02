package com.restaurant.restaurant_spring.dto;

import com.restaurant.restaurant_spring.enums.UserRole;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private  String email;

    private String password;

    private UserRole userRole;

}
