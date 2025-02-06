package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.repository.CourseRepository;
import com.example.FirstApp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student addStudent(Student student){
        return studentRepository.save(student);
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).orElse(new Student());
    }

    public List<Student> getAllStudents(){
        List<Student> courses = studentRepository.findAll();
        return courses;
    }

    public List<Student> deleteStudentById(Long id){
        studentRepository.deleteById(id);
        return getAllStudents();
    }

    public void updateStudentById(Long id, Student student){
        Student c = studentRepository.findById(id).orElse(new Student());

        if(c.getStudentId() == null)
            return;

        c.setFirstName(student.getFirstName());
        c.setLastName(student.getLastName());
        c.setAddedDate(student.getAddedDate());
        c.setEmailId(student.getEmailId());
        c.setGuardian(student.getGuardian());
        c.setCourseList(student.getCourseList());

        studentRepository.save(c);
    }

}
