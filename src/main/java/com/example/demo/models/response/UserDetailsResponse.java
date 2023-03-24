package com.example.demo.models.response;

import lombok.*;

// Response model class for user details
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UserDetailsResponse {

    private String firstName;

    private String lastName;

    private String userId;

    private String email;

    private int age;

    private long phoneNumber;
}