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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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


    //baki
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "courseId"),
            inverseJoinColumns = @JoinColumn(name = "studentId")
    )
    private List<Student> studentList;

    //    @OneToOne(mappedBy = "course")
//    private CourseMaterial courseMaterial;

//
//    @ManyToOne
//    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
//    //many side will have FK
//    private Teacher teacher;

}
