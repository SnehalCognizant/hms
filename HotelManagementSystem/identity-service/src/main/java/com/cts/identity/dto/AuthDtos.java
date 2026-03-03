package com.cts.identity.dto;


import com.cts.identity.entity.User;
import jakarta.validation.constraints.*;

public class AuthDtos {
    public record ApiResponse<T>(boolean success, String message, T data, Object errors) {}
    public record RegisterRequest(@NotBlank String name, @Email @NotBlank String email, String phone, @Size(min=8) String password, @NotNull String role) {}
    public record LoginRequest(@Email @NotBlank String email, @NotBlank String password) {}
    public record UpdateUserRequest(@NotBlank String name, String phone) {}
    public record UserResponse(Long userId, String name, String email, String phone, String role) {}
    public record LoginResponse(String token, Long userId, String name, String role) {}
}
