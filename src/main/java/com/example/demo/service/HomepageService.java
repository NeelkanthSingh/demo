package com.example.demo.service;

import com.example.demo.entity.UserDetails;
import com.example.demo.models.request.UserDetailsRequest;
import com.example.demo.models.response.UserDetailsResponse;
import com.example.demo.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.commons.validator.routines.EmailValidator;


// Service class to save the user details
@Service
public class HomepageService {

    @Autowired
    public UserDetailsRepository userDetailsRepository;

    // Method to save the user details
    public ResponseEntity<String> saveUserDetails(UserDetailsRequest userDetailsRequest){

        UserDetails userDetails = new UserDetails();

        userDetails.setAge(userDetailsRequest.getAge());
        userDetails.setEmailId(userDetailsRequest.getEmail());
        userDetails.setPhoneNumber(userDetailsRequest.getPhoneNumber());
        userDetails.setLastName(userDetailsRequest.getLastName());
        userDetails.setFirstName(userDetailsRequest.getFirstName());

        userDetailsRepository.save(userDetails);
        return ResponseEntity.status(200).body("User Details are saved");
    }

    public ResponseEntity<UserDetailsResponse> getUserDetails(String identifier){
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        UserDetails userDetails;

        if(isValidEmail(identifier)){
            userDetails = userDetailsRepository.findByEmailId(identifier);
        }else{
            userDetails = userDetailsRepository.findByUserId(identifier);
        }

        if(userDetails == null){
            return ResponseEntity.notFound().build();
        }

        userDetailsResponse.setUserId(userDetails.getUserId());
        userDetailsResponse.setAge(userDetails.getAge());
        userDetailsResponse.setPhoneNumber(userDetails.getPhoneNumber());
        userDetailsResponse.setLastName(userDetails.getLastName());
        userDetailsResponse.setFirstName(userDetails.getFirstName());
        userDetailsResponse.setEmail(userDetails.getEmailId());

        return ResponseEntity.ok(userDetailsResponse);
    }

    private boolean isValidEmail(String identifier) {
        // Create an instance of EmailValidator
        EmailValidator validator = EmailValidator.getInstance();

        // Validate the email address
        return validator.isValid(identifier);
    }
}
