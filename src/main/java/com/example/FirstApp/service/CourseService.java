package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public ResponseEntity<?> addCourse(Course course){
        if(course.getCourseName() ==  null){
            return new ResponseEntity<>("course name required", HttpStatus.BAD_REQUEST);
        }

        if(course.getCourseDescription() ==  null){
            return new ResponseEntity<>("course Description required", HttpStatus.BAD_REQUEST);
        }

        CourseDTO dto = new CourseDTO();
        dto.setCourseName(course.getCourseName());
        dto.setCourseDes(course.getCourseDescription());

        courseRepository.save(course);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    public ResponseEntity<?> getCourseById(Long id){
       return courseRepository.findById(id)
                        .map(course -> {
                                    CourseDTO courseDTO = new CourseDTO();
                                    courseDTO.setCourseDes(course.getCourseDescription());
                                    courseDTO.setCourseName(course.getCourseName());

                                    return ResponseEntity.ok(courseDTO);
                                }
                        )
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public List<CourseDTO> getAllCourses(){
        List<CourseDTO> result = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();

        for(Course x : courses) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName(x.getCourseName());
            courseDTO.setCourseDes(x.getCourseDescription());

            result.add(courseDTO);
        }
        return result;
    }

    public ResponseEntity<?>  deleteCourseById(Long id){
        ResponseEntity<?> response = getCourseById(id);

        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return new ResponseEntity<>("No such record",HttpStatus.NOT_FOUND);

        courseRepository.deleteById(id);
        return new ResponseEntity<>("Course deleted",HttpStatus.ACCEPTED);
    }

    public  ResponseEntity<?> updateCourseById(Long id, Course course){
        Course c = courseRepository.findById(id).orElseGet(null);

        if(c == null)
            return new ResponseEntity<>("No such record",HttpStatus.NOT_FOUND);

        if(course.getCourseName() != null)
            c.setCourseName(course.getCourseName());

        if(course.getCourseDescription() != null)
            c.setCourseDescription(course.getCourseDescription());

        courseRepository.save(c);
        CourseDTO courseDTO = new CourseDTO(c.getCourseName(), c.getCourseDescription());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseDTO);
    }



}
