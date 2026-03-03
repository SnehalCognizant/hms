package com.cts.identity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password;
    private String phone;
    private String role; // ROLE_ADMIN or ROLE_USER
}
