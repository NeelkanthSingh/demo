package com.example.demo.models.request;

import com.example.demo.security.Role;
import lombok.*;

// Request Model class for user details
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@ToString
public class AuthenticationRequest {
    private String email;
    private String password;
}
