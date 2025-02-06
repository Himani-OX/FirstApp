package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.service.CourseService;
import com.example.FirstApp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public List<Student> getStudents(){
       return studentService.getAllStudents();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student){
        return studentService.addStudent(student);
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable Long id){
        return studentService.getStudentById(id);
    }

    @DeleteMapping({"/delete/{id}", "/{id}"})
    public List<Student> deleteStudentById(@PathVariable("id") Long id){
        return  studentService.deleteStudentById(id);
    }

    @PutMapping("/update/{id}")
    public Student updateStudentById(@PathVariable("id") Long id, @RequestBody Student student){
        studentService.updateStudentById(id,student);
        return studentService.getStudentById(id);
    }

}
