package com.example.FirstApp.repository;

import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = "update student set added_date = ?2 where student_id = ?1"
    )
    int updatestudentDateById(Long id, LocalDate date);
}
