package com.example.demo.controller;

import com.example.demo.models.request.UserDetailsRequest;
import com.example.demo.models.response.UserDetailsResponse;
import com.example.demo.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

// Controller class to store the user details
@Controller
@RequestMapping("")
public class HomepageController {

    @Autowired
    public HomepageService homepageService;

    // Method to store the user details
    @PostMapping("/homepage")
    ResponseEntity<String> storeUserDetails(@RequestBody(required = true) UserDetailsRequest userDetailsRequest) {
        return homepageService.saveUserDetails(userDetailsRequest);
    }

    @GetMapping("/homepage")
    ResponseEntity<UserDetailsResponse> getUserDetails(String identifier){
        return homepageService.getUserDetails(identifier);
    }



}

