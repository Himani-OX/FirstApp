package com.example.FirstApp.repository;

import com.example.FirstApp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findByFirstName(String firstName);

    //like pattern
    public List<Student> findByFirstNameContaining(String name);

    //not null fields records
    public List<Student> findByLastNameNotNull();

    //embedding class attribute => findBy + name of class + field name of that class
    public List<Student> findByGuardianName(String name);

    @Query(value = "select * from student where first_name like %?1%", nativeQuery = true)
    public List<Student> findAll(String filterBy);
}
