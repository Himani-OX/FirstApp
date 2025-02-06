package com.example.FirstApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(
//        uniqueConstraints = {
//                @UniqueConstraint(
//                        name = "constraintName",
//                        columnNames = {"emailId"}
//                )
//        }

)
public class Student {

    @Id
    @SequenceGenerator(name = "student_id", sequenceName = "student_sequence_id", allocationSize = 1)
    //generator name => student_id
    //sequence name => table name  for generating sequence in mysql=> student_sequence_id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id")
    private Long studentId;
    private String firstName;
    private String lastName;

    private LocalDate addedDate;

    @Column(nullable = false, unique = true)  //allows only unique value and not null
    private String emailId;

    @Embedded
    @AttributeOverrides({  //sets the attributes properties to be used at embedding side
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "guardianName")
            ),
            @AttributeOverride(
                    name = "email",
                    column = @Column(name = "guardianEmail")
            ),
            @AttributeOverride(
                    name = "mobile",
                    column = @Column(name = "guardianMobile", length = 10)
            )
    }
    )
    private Guardian guardian;


    @ManyToMany(mappedBy = "studentList")
    private List<Course> courseList;
}
