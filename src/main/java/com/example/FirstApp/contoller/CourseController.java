package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<CourseDTO> getCourses(){
       return courseService.getAllCourses();
    }

    @PostMapping
    public CourseDTO addCourse(@RequestBody Course course){
        return courseService.addCourse(course);
    }

    @GetMapping("/{id}")
    public CourseDTO getCourseById(@PathVariable Long id){
        return courseService.getCourseById(id);
    }

    @DeleteMapping({"/delete/{id}", "/{id}"})
    public List<CourseDTO> deleteCourseById(@PathVariable("id") Long id){
        return  courseService.deleteCourseById(id);
    }

    @PutMapping("/update/{id}")
    public CourseDTO updateCourseById(@PathVariable("id") Long id, @RequestBody Course course){
        courseService.updateCourseById(id,course);
        return courseService.getCourseById(id);
    }

}
