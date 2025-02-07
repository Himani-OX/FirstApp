package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.service.CourseService;
import com.example.FirstApp.service.StudentService;
import com.example.FirstApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @PostMapping({"/added-by/{user}", "/add"})
    public ResponseEntity<?> addStudent( @RequestBody Student student, @PathVariable(value = "user", required = false) Long id){
        System.out.println("in");
        if(id == null)
            id = student.getAddedBY().getId();
        return studentService.addStudent(student, id);
    }

    @GetMapping
    public ResponseEntity<?> getStudents(){
        return new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @DeleteMapping({"/delete/{id}", "/{id}"})
    public ResponseEntity<?> deleteStudentById(@PathVariable("id") Long id){
        return  studentService.deleteStudentById(id);
    }

    @PutMapping({"/update/{id}", "/{id}"})
    public ResponseEntity<?> updateStudentById(@PathVariable("id") Long id, @RequestBody Student student){
        System.out.println("ub");
        studentService.updateStudentById(id,student);
        return studentService.getStudentById(id);
    }

}
