package com.example.FirstApp.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "studentList"})
public class Course {

    @Id
    @SequenceGenerator(name = "course_id", sequenceName = "course_sequence_id", allocationSize = 1)
    //generator name => student_id
    //sequence name => table name  for generating sequence in mysql=> student_sequence_id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id")
    private Long courseId;

    @Column(nullable = false)
    private String courseName;

    @Column(nullable = false)
    private String courseDescription;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime addedDateTime;

    @LastModifiedDate
    private LocalDateTime updatedDateTime;

    @ManyToMany(mappedBy = "courseList")
    private List<Student> studentList;

    public Course(String s, String s1) {
        courseName = s;
        courseDescription = s1;
    }


    //    @OneToOne(mappedBy = "course")
//    private CourseMaterial courseMaterial;

//
//    @ManyToOne
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    //many side will have FK
//    private Teacher teacher;

}
