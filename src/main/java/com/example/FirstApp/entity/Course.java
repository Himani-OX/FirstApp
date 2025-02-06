package com.example.FirstApp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @SequenceGenerator(name = "course_id", sequenceName = "course_sequence_id", allocationSize = 1)
    //generator name => student_id
    //sequence name => table name  for generating sequence in mysql=> student_sequence_id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id")
    private Long courseId;
    private String courseName;
    private LocalDate addedDate;

    @OneToOne(mappedBy = "course")
    private CourseMaterial courseMaterial;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "studentId")
    )
    private List<Student> studentList;


    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    //many side will have FK
    private Teacher teacher;

}
