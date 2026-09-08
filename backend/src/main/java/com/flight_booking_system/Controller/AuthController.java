package com.flight_booking_system.Controller;

import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.flight_booking_system.DTO.AuthRequest;
import com.flight_booking_system.DTO.AuthResponse;
import com.flight_booking_system.Entity.UserInfo;
import com.flight_booking_system.Repository.User_Repository;
import com.flight_booking_system.Service.User_Service;
import com.flight_booking_system.Utility.JwtUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/auth")
@Tag(name = "Authentication", description = "Authentication related APIs")
public class AuthController {
	
	@Autowired
	private User_Repository repository;
	@Autowired
	private User_Service service;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	 @Operation(summary = "Register ", description = "Add  registration")
	    @ApiResponse(responseCode = "201", description = "Registration created successfully")
	@PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody AuthRequest request) {
        if (repository.findByEmail(request.email()).isPresent()) {
            return new ResponseEntity<>(new AuthResponse("User already exists", null), HttpStatus.CONFLICT);
        }

        service.createUser(UserInfo.builder()
                .email(request.email())
                .password(request.password()) 
                .build());

        return new ResponseEntity<>(new AuthResponse("Registered successfully", null), HttpStatus.CREATED);
    }
	 @Operation(summary = "Login  ", description = "Login to access")
	    @ApiResponse(responseCode = "200", description = "Login  successfully")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> loginUser(@RequestBody AuthRequest request) {
		Optional<UserInfo> userOptional = repository.findByEmail(request.email());

		if (userOptional.isEmpty()) {
			return new ResponseEntity<>(new AuthResponse("User not registered", null), HttpStatus.UNAUTHORIZED);
		}

		UserInfo user = userOptional.get();

		if (!passwordEncoder.matches(request.password(), user.getPassword())) {
			return new ResponseEntity<>(new AuthResponse("Invalid password", null), HttpStatus.UNAUTHORIZED);
		}

		String token = jwtUtil.generateToken(request.email());

		return ResponseEntity.ok(new AuthResponse("Login successful", token));
	}
}
