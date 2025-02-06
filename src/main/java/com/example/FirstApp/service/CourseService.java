package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO addCourse(Course course){
        CourseDTO dto = new CourseDTO();
        dto.setCourseName(course.getCourseName());
        dto.setAddedDate(course.getAddedDate());

        courseRepository.save(course);

        return dto;
    }

    public CourseDTO getCourseById(Long id){
        CourseDTO courseDTO = new CourseDTO();

        Course course = courseRepository.findById(id).orElse(new Course());
        if(course.getCourseId() == null)
            return new CourseDTO();

        courseDTO.setAddedDate(course.getAddedDate());
        courseDTO.setCourseName(course.getCourseName());
        return courseDTO;
    }

    public List<CourseDTO> getAllCourses(){
        List<CourseDTO> result = new ArrayList<>();
        List<Course> courses = courseRepository.findAll();

        for(Course x : courses) {
            CourseDTO courseDTO = new CourseDTO();
            courseDTO.setCourseName(x.getCourseName());
            courseDTO.setAddedDate(x.getAddedDate());

            result.add(courseDTO);
        }
        return result;
    }

    public List<CourseDTO> deleteCourseById(Long id){
        courseRepository.deleteById(id);
        return getAllCourses();
    }

    public void updateCourseById(Long id, Course course){
        Course c = courseRepository.findById(id).orElse(new Course());

        if(c.getCourseId() == null)
            return;

        c.setCourseName(course.getCourseName());
        c.setAddedDate(course.getAddedDate());
        courseRepository.save(c);
    }



}
