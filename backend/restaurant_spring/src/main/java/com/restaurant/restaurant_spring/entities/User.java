package com.restaurant.restaurant_spring.entities;

import com.restaurant.restaurant_spring.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private  String email;

    private String password;

    private UserRole userRole;

}
