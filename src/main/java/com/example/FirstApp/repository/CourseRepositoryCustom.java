package com.example.FirstApp.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepositoryCustom {

//    private final EntityManagerFactory entityManagerFactory;
//
//    @Autowired
//    public CourseRepositoryCustom(EntityManagerFactory entityManagerFactory) {
//        this.entityManagerFactory = entityManagerFactory;
//    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public int updateCourseName(Long courseId, String newName) {
        String sql = "UPDATE course SET course_name = :newName WHERE course_id = :id";

        if(entityManager == null) {
            System.out.println("null entitymanage");
            return 0;
        }

        Query query = entityManager.createNativeQuery(sql);
        query.setParameter("newName", newName);
        query.setParameter("id", courseId);
        return query.executeUpdate(); // Returns number of rows updated
    }
}
