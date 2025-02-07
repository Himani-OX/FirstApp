package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses(){
        List<CourseDTO> response = courseService.getAllCourses();
        if(response.isEmpty())
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @DeleteMapping({"/delete/{id}", "/{id}"})
    public ResponseEntity<?> deleteCourseById(@PathVariable("id") Long id){
        return  courseService.deleteCourseById(id);
    }

    @PutMapping({"/update/{id}", "/{id}"})
    public ResponseEntity<?>  updateCourseById(@PathVariable("id") Long id, @RequestBody Course course){
        return courseService.updateCourseById(id,course);
    }

}
