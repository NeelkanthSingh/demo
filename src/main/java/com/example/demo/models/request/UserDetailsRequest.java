package com.example.demo.models.request;

import lombok.*;

// Request Model class for user details
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@ToString
public class UserDetailsRequest {

    private String firstName;

    private String lastName;

    private String userId;

    private String email;

    private int age;

    private long phoneNumber;
}
