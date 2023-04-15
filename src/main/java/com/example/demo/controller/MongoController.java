package com.example.demo.controller;

import com.example.demo.mongodb.Address;
import com.example.demo.mongodb.Gender;
import com.example.demo.mongodb.Student;
import com.example.demo.mongodb.StudentRepository;
import com.example.demo.service.MongoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mongo")
public class MongoController {

    private final MongoService mongoService;

    @PostMapping
    public ResponseEntity<String> insertStudent(){
        mongoService.insertStudent();
        return ResponseEntity.ok("Student inserted successfully");
    }
}
