package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Address;
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
        if(id == null)
            id = student.getAddedBY().getId();
        return studentService.addStudent(student, id);
    }

    @GetMapping
    public ResponseEntity<?> getStudents(@RequestParam(value = "sortBy", required = false) String sortBy, @RequestParam(value = "order", required = false) String order,
                                         @RequestParam(value = "filterBy", required=false) String filterBy){
        System.out.println(filterBy);
        return new ResponseEntity<>(studentService.getAllStudents(sortBy, order,filterBy), HttpStatus.ACCEPTED);
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

    @PostMapping({"/{studentId}/add/course/{courseId}"})
    public ResponseEntity<?> addStudentCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        return studentService.addStudentCourse(studentId,courseId);
    }

    @DeleteMapping({"/{studentId}/delete/course/{courseId}"})
    public ResponseEntity<?> deleteStudentCourse(@PathVariable("studentId") Long studentId, @PathVariable("courseId") Long courseId){
        return studentService.removeCourse(studentId, courseId);
    }

    @PostMapping("/{studentId}/add/address/")
    public ResponseEntity<?> addStudentAddress(@PathVariable Long studentId, @RequestBody Address address){
        return studentService.addStudentAddress(studentId,address);
    }

    @DeleteMapping("/{studentId}/delete/address/{addressId}")
    public ResponseEntity<?> addStudentAddress(@PathVariable Long studentId, @PathVariable Long addressId){
        return studentService.removeStudentAddress(studentId,addressId);
    }





}
