package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.DTO.StudentDTO;
import com.example.FirstApp.entity.*;
import com.example.FirstApp.repository.CourseRepository;
import com.example.FirstApp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

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

    public List<StudentDTO> getAllStudents(String sortBY,String order, String filterBy){
        Sort sortBy = Sort.unsorted();

        if(sortBY != null) {
            sortBy = order != null && order.equals("desc") ? Sort.by(sortBY).descending() : Sort.by(sortBY).ascending();
        }

        List<Student> students = filterBy != null ?
                studentRepository.findAll(filterBy) :
                studentRepository.findAll(sortBY);

        return getStudentDTO(students);
    }

    public List<StudentDTO> getStudentDTO(List<Student> students){
        return students.stream().map(x -> new StudentDTO(
                        x.getFirstName() + " " + x.getLastName(),
                        x.getEmailId() ,
                        x.getGuardian(),
                        x.getAddress(),
                        x.getCourseList().stream().map(Course::getCourseId).toList()))
                .toList();
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

    public ResponseEntity<?> addStudentCourse(Long studentId, Long courseId){
        Student student= studentRepository.findById(studentId).orElse(null);
        Course course = courseService.findCourseById(courseId);

        if(student == null || course == null){
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }

//        if(student.getCourseList().contains(course)){
//            return new ResponseEntity<>("Already Exist", HttpStatus.BAD_REQUEST);
//        }

        student.getCourseList().add(course);
        studentRepository.save(student);
        return getStudentById(studentId);
    }

    public ResponseEntity<?> removeCourse(Long studentId, Long courseId) {
        Student student= studentRepository.findById(studentId).orElse(null);
        Course course = courseService.findCourseById(courseId);

        if(student == null || course == null || !student.getCourseList().contains(course)){
            return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
        }

        student.getCourseList().remove(course);
        studentRepository.save(student);
        return getStudentById(studentId);
    }

    public ResponseEntity<?> addStudentAddress(Long studentId, Address address) {
        Student student = studentRepository.findById(studentId).orElse(null);

        if(student == null || address == null)
            return new ResponseEntity<>(notFound(), HttpStatus.NOT_FOUND);

        address.setStudent(student);
        student.getAddress().add(address);
        studentRepository.save(student);

        return getStudentById(studentId);
    }

    public ResponseEntity<?> removeStudentAddress(Long studentId, Long addressId) {
        Student student = studentRepository.findById(studentId).orElse(null);
        Address studentAddress = student.getAddress()
                .stream().filter(x -> x.getAddressId().equals(addressId))
                .findFirst()
                .orElse(null);

        if(student == null || studentAddress == null)
            return notFound();

        student.getAddress().remove(studentAddress);
        studentRepository.save(student);

        return getStudentById(studentId);
    }


    //baki
//    public ResponseEntity<?> removeStudentAddress(Long studentId, Long addressId) {
//        Student student = studentRepository.findById(studentId).orElse(null);
//        Student student = studentRepository.findById(studentId).orElse(null);
//
//        if(student == null || address == null)
//            return new ResponseEntity<>(notFound(), HttpStatus.NOT_FOUND);
//
//        student.getAddress().add(address);
//        studentRepository.save(student);
//
//        return getStudentById(studentId);
//    }
}
