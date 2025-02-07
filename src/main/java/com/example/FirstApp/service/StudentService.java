package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.*;
import com.example.FirstApp.repository.CourseRepository;
import com.example.FirstApp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    public ResponseEntity<?> addStudent(Student student, Long userId){
        User user = userService.getUser(userId);

        System.out.println(user);

        if(user == null || student == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such User exist");

        if(student.getLastName() == null || student.getFirstName() == null || student.getEmailId() == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Data Not sufficient");
        }

        Guardian guardian = student.getGuardian();
        if(guardian == null)
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("guardian details missing");


        if(guardian.getEmail() == null || guardian.getMobile() == null || guardian.getName() == null){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Guardian Details missing");
        }

        student.setAddedBY(user);
        studentRepository.save(student);

        return new ResponseEntity<>(student,HttpStatus.CREATED);
    }

    public ResponseEntity<?> getStudentById(Long id){
        return studentRepository.findById(id)
                .map(ResponseEntity :: ok)
                .orElseGet(() -> (ResponseEntity<Student>) notFound());
    }

    public List<Student> getAllStudents(){
        List<Student> students = studentRepository.findAll();
        return students;
    }

    public ResponseEntity<?> deleteStudentById(Long id){
        Student student = studentRepository.findById(id).orElse(null);
        if(student == null)
            return notFound();

        studentRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Deleted");
    }

    public ResponseEntity<?> notFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No such data");
    }

    public ResponseEntity<?> updateStudentById(Long id, Student student){
        Student c = studentRepository.findById(id).orElse(null);
        if(c == null)
            return notFound();

        if(student.getFirstName() != null)
            c.setFirstName(student.getFirstName());

        if(c.getLastName() != null)
            c.setLastName(student.getLastName());

        if(c.getEmailId() != null)
            c.setEmailId(student.getEmailId());

        if(student.getGuardian() != null)
            c.setGuardian(student.getGuardian());


        studentRepository.save(c);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Updated");
    }

}
