package com.example.demo.models.request;

import lombok.*;

// Model class to store the user details
@Getter@Setter@NoArgsConstructor@AllArgsConstructor@ToString
public class HomepageRequest {

    private String firstName;

    private String lastName;

    private String userId;

    private String email;

    private int age;

    private long phoneNumber;
}
