package com.example.demo.controller;

import com.example.demo.models.request.HomepageRequest;
import com.example.demo.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
    ResponseEntity<String> storeUserDetails(@RequestBody(required = true) HomepageRequest homepageRequest) {
        return homepageService.saveUserDetails(homepageRequest);
    }

}

