package com.example.demo.controller;

import com.example.demo.mongodb.Address;
import com.example.demo.mongodb.Gender;
import com.example.demo.mongodb.Student;
import com.example.demo.mongodb.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mongo")
public class MongoController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping
    public void insertStudent(){


        Address address = new Address(
                "India",
                "Delhi",
                "110001"
        );

        List<String> favouriteSubjects = List.of("Maths", "Science");

        Student student = new Student(
                "Neeraj",
                "Yadav",
                "neeraj.yadav@gmail.com",
                Gender.MALE,
                address,
                favouriteSubjects,
                BigDecimal.TEN,
                LocalDateTime.now()
        );

        studentRepository.insert(student);

    }
}
