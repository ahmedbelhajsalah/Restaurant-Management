package com.restaurant.restaurant_spring.controller;

import com.restaurant.restaurant_spring.dto.AuthenticationRequest;
import com.restaurant.restaurant_spring.dto.AuthenticationResponse;
import com.restaurant.restaurant_spring.dto.SignupRequest;
import com.restaurant.restaurant_spring.dto.UserDto;
import com.restaurant.restaurant_spring.services.auth.AuthService;
import com.restaurant.restaurant_spring.services.auth.jwt.UserDetailsServiceImpl;
import com.restaurant.restaurant_spring.util.JWTUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JWTUtil jwtUtil;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager, UserDetailsServiceImpl userDetailsService, JWTUtil jwtUtil){
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest){
        UserDto createdUserDto = authService.createUser(signupRequest);
        if(createdUserDto == null){
            return new ResponseEntity<>("user is not created", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse httpServletResponse) throws IOException {
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        } catch (BadCredentialsException e){
            throw new BadCredentialsException("incorrect username or password");
        } catch (DisabledException e){
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "user not active");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());


        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        return new AuthenticationResponse(jwt);
    }
}
