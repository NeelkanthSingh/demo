package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// Controller class to store the user details
@RestController
@RequestMapping("/demo")
public class HomepageController {

    @GetMapping("/homepage")
    ResponseEntity<String> getHome(){
        return ResponseEntity.ok("You have reached homepage.");
    }

}