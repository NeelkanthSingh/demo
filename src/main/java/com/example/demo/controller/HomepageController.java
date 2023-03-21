package com.example.demo.controller;

import com.example.demo.models.request.UserDetailsRequest;
import com.example.demo.models.response.UserDetailsResponse;
import com.example.demo.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Controller class to store the user details
@Controller
@RequestMapping("")
public class HomepageController {

    @Autowired
    public HomepageService homepageService;

    // Create a logger instance
    Logger logger = LoggerFactory.getLogger(HomepageController.class);

    // Method to store the user details
    @PostMapping("/homepage")
    ResponseEntity<String> storeUserDetails(@RequestBody(required = true) UserDetailsRequest userDetailsRequest) {
        logger.info("This is the User Details: " + userDetailsRequest.toString());
        return homepageService.saveUserDetails(userDetailsRequest);
    }

    @GetMapping("/homepage")
    ResponseEntity<UserDetailsResponse> getUserDetails(@RequestParam String identifier){
        logger.info("This is the Identifier " + identifier);
        return homepageService.getUserDetails(identifier);
    }



}

