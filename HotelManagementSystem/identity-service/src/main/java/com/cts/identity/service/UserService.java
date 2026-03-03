package com.cts.identity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cts.identity.dto.AuthRequest;
import com.cts.identity.dto.AuthResponse;
import com.cts.identity.dto.UserRegistrationRequest;
import com.cts.identity.entity.User;
import com.cts.identity.repository.UserRepository;
import com.cts.identity.util.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User registerUser(UserRegistrationRequest request) {
        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email: " + request.getEmail());
        }
        
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhone(request.getPhone());
        user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");
        user.setEnabled(true);
        
        return userRepository.save(user);
    }

    public AuthResponse authenticateUser(AuthRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        
        if (!userOptional.isPresent()) {
            throw new RuntimeException("User not found with email: " + request.getEmail());
        }
        
        User user = userOptional.get();
        
        if (!user.isEnabled()) {
            throw new RuntimeException("User account is disabled");
        }
        
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole(), user.getUserId());
        
        AuthResponse response = new AuthResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setUserId(user.getUserId());
        response.setName(user.getName());
        
        return response;
    }

    public User registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists with this email: " + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(user.getRole() != null ? user.getRole() : "ROLE_USER");
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
