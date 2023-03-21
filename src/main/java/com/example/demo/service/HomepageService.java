package com.example.demo.service;

import com.example.demo.entity.UserDetails;
import com.example.demo.models.request.HomepageRequest;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


// Service class to save the user details
@Service
public class HomepageService {

    @Autowired
    public UserDetailsRepository userDetailsRepository;

    // Method to save the user details
    public ResponseEntity<String> saveUserDetails(HomepageRequest homepageRequest){

        UserDetails userDetails = new UserDetails();

        userDetails.setAge(homepageRequest.getAge());
        userDetails.setEmailId(homepageRequest.getEmail());
        userDetails.setPhoneNumber(homepageRequest.getPhoneNumber());
        userDetails.setLastName(homepageRequest.getLastName());
        userDetails.setFirstName(homepageRequest.getFirstName());

        userDetailsRepository.save(userDetails);
        return ResponseEntity.status(200).body("User Details are saved");
    }
}
