package com.example.FirstApp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.apache.logging.log4j.util.Lazy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.repository.cdi.Eager;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//@Table => used for defining table name is DB, for defining unique constraints
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({"handler", "hibernateLazyInitializer", "addedBY"})
public class Student {

    @Id
    @SequenceGenerator(name = "student_id", sequenceName = "student_sequence_id", allocationSize = 1)
    //generator name => student_id
    //sequence name => table name  for generating sequence in mysql=> student_sequence_id

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id")
    private Long studentId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @CreatedDate
    private LocalDate addedDateTime;

    @LastModifiedDate
    private LocalDate updateDateTime;

    @Column(nullable = false, unique = true)  //allows only unique value and not null
    private String emailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "addedBy_user_id", referencedColumnName = "id")
//    @JsonManagedReference
    private User addedBY;

    @Embedded
    @AttributeOverrides({  //sets the attributes properties to be used at embedding side
            @AttributeOverride(
                    name = "name",
                    column = @Column(name = "guardianName", nullable = false)
            ),
            @AttributeOverride(
                    name = "email",
                    column = @Column(name = "guardianEmail", nullable = false)
            ),
            @AttributeOverride(
                    name = "mobile",
                    column = @Column(name = "guardianMobile", length = 10, nullable = false)
            )
    }
    )
    private Guardian guardian;

    @ManyToMany
    @JoinTable(
            name = "student_course",
            joinColumns = @JoinColumn(name = "courseId"), //replace names
            inverseJoinColumns = @JoinColumn(name = "studentId")
    )
    private List<Course> courseList;


    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "student", orphanRemoval = true) //removes from DB
    private List<Address> address;
}
