package com.example.demo.service;

import com.example.demo.mongodb.Address;
import com.example.demo.mongodb.Gender;
import com.example.demo.mongodb.Student;
import com.example.demo.mongodb.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MongoService {

    private final StudentRepository studentRepository;

    public void insertStudent() {

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
