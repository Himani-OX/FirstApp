package com.example.FirstApp.paging;


import com.example.FirstApp.entity.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
@Builder
public class PageRequestForCourse {

    @Builder.Default
    private Integer currentPage = 0;

    @Builder.Default
    private Integer pageSize = 10;
    private Integer totalPages;
    private Integer totalElements;
    private SortBy sortBy;
    private List<Search> searchList;


    //query construction
    public String getQuery(){
        StringBuilder orderBy = new StringBuilder(" order by ");
        if(sortBy != null){
            if(sortBy.getFieldName() != null)
                orderBy.append(sortBy.getFieldName() + " ");
            else
                orderBy.append(" course_id ");

            if(sortBy.getOrder() != null)
                orderBy.append(sortBy.getOrder());
            else
                orderBy.append(" ASC ");
        }else{
            orderBy.append(" course_id ASC ");
        }

        StringBuilder whereCodition = new StringBuilder("");
        if(searchList != null){
            for(int i =0 ; i < searchList.size() ; i++){
                Search searchField = searchList.get(i);
                if(searchField.getFieldName() != null && searchField.getKeyword() != null)
                    whereCodition.append(searchField.getFieldName()).append(" like  '%").append(searchField.getKeyword()).append("%' ");

                if(i != searchList.size() - 1)
                    whereCodition.append(" or ");
            }
        }

        StringBuilder query = new StringBuilder(" select * from course ");
        if(searchList != null && !searchList.isEmpty())
            query.append(" where ");

        return query.append(whereCodition).append(orderBy).append(" limit :pageSize offset :offset ").toString();
    }

    public List<Course> getCourses(EntityManager entityManager) {
        Query query = entityManager.createNativeQuery(getQuery(), Course.class);

        query.setParameter("pageSize", pageSize);
        query.setParameter("offset" , currentPage * pageSize);

        return query.getResultList();
    }
}
