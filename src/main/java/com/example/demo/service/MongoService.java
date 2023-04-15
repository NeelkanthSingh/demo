package com.example.demo.service;

import com.example.demo.mongodb.Address;
import com.example.demo.mongodb.Gender;
import com.example.demo.mongodb.Student;
import com.example.demo.mongodb.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;

@RequiredArgsConstructor
@Service
public class MongoService {

    private final StudentRepository studentRepository;
    private final MongoTemplate mongoTemplate;

    public List<Student> findStudentQuery() {
        Query query = new Query();
        query.addCriteria(Criteria.where("firstName").is("Neeraj"));

        List<Student> students = mongoTemplate.find(query, Student.class);

        System.out.println(students);
        return students;

    }


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

    public List<Student> findStudentByName(String name) {
        return studentRepository.findByFirstName(name);
    }
}
