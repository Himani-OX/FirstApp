package com.example.FirstApp.repository;

import com.example.FirstApp.entity.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private studentRepository studentRepository;

    @Autowired
    private studentMaterialRepository studentMaterialRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @PersistenceContext
    private EntityManager entityManager;


    @Test
    public void saveStudent(){
        Student s = Student.builder()
                .emailId("xyz@gmail.com")
                .firstName("himani")
                .lastName("trivedi")
//                .guardianName("bkbjk")
//                .guardianEmail("bhdhjk@emial.com")
//                .guardianMobile("454985")
                .build();

        studentRepository.save(s);
    }

    @Test
    public void saveStudnetWithGuardian(){
        Guardian guardian = new Guardian("demo", "gejkg@ff.com", "242343557");
        Student course = Student.builder()
                .firstName("nidhi")
                .lastName("trivedi")
                .emailId("sgsdgsfg")
                .guardian(guardian)
                .build();
        studentRepository.save(course);
    }

    @Test
    public void getStudentByFirstName(){
        List<Student> courses = studentRepository.findByFirstName("nidhi");
        for(Student s: courses){
            System.out.println(s);
        }
    }

    @Test
    public void addstudent(){
        Student course = course.builder()
                .studentName("Spring Boot")
                .addedDate(LocalDate.now())
                .build();
        studentRepository.save(course);
    }


    @Test
    public void updatestudent(){
        int id =studentRepository.updatestudentDateById(1l, LocalDate.now());
        System.out.println(id);
    }

    @Test
    public void addstudentMaterial(){
        CourseMaterial CourseMaterial = CourseMaterial.builder()
                .url("dff")
                .course(studentRepository.findById(2l).orElse(new Student()))
                .build();
        studentMaterialRepository.save(CourseMaterial);
    }

    @Test
    public void printAllstudentMaterials(){
        List<CourseMaterial> CourseMaterials = studentMaterialRepository.findAll();
        System.out.println(CourseMaterials);
    }

    @Test
    public void addTeacher(){
//        List<student> studentList = studentRepository.findAll();
        Student c = Student.builder()
                .addedDate(LocalDate.now())
                .studentName("flutter")
                .build();

        Student c2 = Student.builder()
                .addedDate(LocalDate.now())
                .studentName("Ios")
                .build();

        Teacher teacher = Teacher.builder()
                .name("new Teacher")
                .courseList(Arrays.asList(c,c2))
                .build();

        teacherRepository.save(teacher);

    }

    @Test
    public void getAllstudentDetails(){
        List<Student> courses = studentRepository.findAll();
        for(Student x: courses)
            System.out.println(x);
    }

    @Test
    public void addstudentWithAllValues(){
        Student course = course.builder()
                .addedDate(LocalDate.now())
                .studentName("new cource")
                .teacher(teacherRepository.findById(1l).orElse(new Teacher()))
                .studentList(Arrays.asList(studentRepository.findById(1l).orElse(new Course())))
                .studentMaterial(studentMaterialRepository.findById(1l).orElse(new CourseMaterial()))
                .build();
        studentRepository.save(course);
    }

}