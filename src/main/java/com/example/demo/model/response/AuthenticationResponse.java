package com.example.demo.model.response;

import lombok.*;

// Response model class for user details
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString @Builder
public class AuthenticationResponse {

    private String token;
}
