package com.example.demo.model.request;

import lombok.*;

// Request Model class for user details
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@ToString
public class AuthenticationRequest {
    private String email;
    private String password;
}
