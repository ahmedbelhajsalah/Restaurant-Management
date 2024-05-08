package com.restaurant.restaurant_spring.services.auth;

import com.restaurant.restaurant_spring.dto.SignupRequest;
import com.restaurant.restaurant_spring.dto.UserDto;
import com.restaurant.restaurant_spring.entities.User;
import com.restaurant.restaurant_spring.enums.UserRole;
import com.restaurant.restaurant_spring.repositories.UserRepository;
import com.restaurant.restaurant_spring.services.auth.AuthService;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    /* @PostConstruct
    public void createAdminAccount(){
        User adminAccount = userRepository.findByUserRole(UserRole.ADMIN);
        if(adminAccount == null){
            User user = new User();
            user.setUserRole(UserRole.ADMIN);
            user.setName("admin");
            user.setEmail("admin@admin.com");
            user.setPassword(new BCryptPasswordEncoder().encode("admin123"));
            userRepository.save(user);
        }
    } */

    @Override
    public UserDto createUser(SignupRequest signupRequest) {
        User user = new User();
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
        user.setUserRole(UserRole.CUSTOMER);
        User createdUser = userRepository.save(user);
        UserDto createdUserDto = new UserDto();
        createdUserDto.setId(createdUser.getId());
        createdUserDto.setEmail(createdUser.getEmail());
        createdUserDto.setName(createdUser.getName());
        createdUserDto.setPassword(createdUser.getPassword());
        createdUserDto.setUserRole(createdUser.getUserRole());

        return createdUserDto;
    }
}
