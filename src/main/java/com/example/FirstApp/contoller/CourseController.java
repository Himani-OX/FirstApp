package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.paging.PageRequestForCourse;
import com.example.FirstApp.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getCourses(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size){
        System.out.println(page + " " + size);
        if(page != null && size != null)
            return new ResponseEntity<>(courseService.getPageOfCourse(page,size), HttpStatus.OK);

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

    @PostMapping("/upload")
    public ResponseEntity<?> addCourses(@RequestParam("file") MultipartFile file){
        if(file.isEmpty())
            return ResponseEntity.badRequest().body("Empty File");

        return courseService.addCourses(file);

    }

    @PostMapping("/pageRequest")
    public ResponseEntity<?> getPage(@RequestBody PageRequestForCourse pageRequestForCourse){
        System.out.println(pageRequestForCourse);
        return new ResponseEntity<>(courseService.getData(pageRequestForCourse), HttpStatus.OK);
    }

    @PutMapping("/updateRepo")
    public ResponseEntity<?>  updateCourseName(@RequestParam Long courseId, @RequestParam String newName){
        return new ResponseEntity<>(courseService.updateName(courseId,newName), HttpStatus.OK);
    }



}
