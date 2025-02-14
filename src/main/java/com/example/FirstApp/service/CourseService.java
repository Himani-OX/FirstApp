package com.example.FirstApp.service;


import com.example.FirstApp.DTO.CourseDTO;
import com.example.FirstApp.entity.Course;
import com.example.FirstApp.entity.Student;
import com.example.FirstApp.paging.PageRequestForCourse;
import com.example.FirstApp.repository.CourseRepository;
import com.example.FirstApp.repository.CourseRepositoryCustom;
import com.opencsv.CSVReader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseRepositoryCustom courseRepositoryCustom;

    @PersistenceContext
    private EntityManager entityManager;

    public ResponseEntity<?> addCourse(Course course){
        if(course.getCourseName() ==  null){
            return new ResponseEntity<>("course name required", HttpStatus.BAD_REQUEST);
        }

        if(course.getCourseDescription() ==  null){
            return new ResponseEntity<>("course Description required", HttpStatus.BAD_REQUEST);
        }

        CourseDTO dto = new CourseDTO();
        dto.setCourseName(course.getCourseName());
        dto.setCourseDes(course.getCourseDescription());

        courseRepository.save(course);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    public Course findCourseById(Long id){
        return courseRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> getCourseById(Long id){
       return courseRepository.findById(id)
                        .map(course -> {
                                    CourseDTO courseDTO = new CourseDTO();
                                    courseDTO.setCourseDes(course.getCourseDescription());
                                    courseDTO.setCourseName(course.getCourseName());

                                    return ResponseEntity.ok(courseDTO);
                                }
                        )
                                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    public List<CourseDTO> getAllCourses(){
        return getCourseDTOs(courseRepository.findAll());
    }

    public List<CourseDTO> getCourseDTOs(List<Course> courses){
        return courses.stream().map(course ->
            new CourseDTO(course.getCourseName(),course.getCourseDescription())).toList();
    }


    public ResponseEntity<?>  deleteCourseById(Long id){
        ResponseEntity<?> response = getCourseById(id);

        if(response.getStatusCode().equals(HttpStatus.NOT_FOUND))
            return new ResponseEntity<>("No such record",HttpStatus.NOT_FOUND);

        courseRepository.deleteById(id);
        return new ResponseEntity<>("Course deleted",HttpStatus.ACCEPTED);
    }

    public  ResponseEntity<?> updateCourseById(Long id, Course course){
        Course c = courseRepository.findById(id).orElseGet(null);

        if(c == null)
            return new ResponseEntity<>("No such record",HttpStatus.NOT_FOUND);

        if(course.getCourseName() != null)
            c.setCourseName(course.getCourseName());

        if(course.getCourseDescription() != null)
            c.setCourseDescription(course.getCourseDescription());

        courseRepository.save(c);
        CourseDTO courseDTO = new CourseDTO(c.getCourseName(), c.getCourseDescription());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(courseDTO);
    }


    public ResponseEntity<?> addCourses(MultipartFile file) {
        try{
            CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()));
            List<Course> courses = new ArrayList<>(100);
            String[] line;
            csvReader.readNext();

            while((line = csvReader.readNext()) != null){
                Course course = new Course(line[0], line[1]);
                courses.add(course);
            }

            courseRepository.saveAll(courses);
            return ResponseEntity.ok("Records Added");
        }catch (Exception e){
            return ResponseEntity.badRequest().body("file not read");
        }

    }

    public List<CourseDTO> getPageOfCourse(Integer page, Integer size) {
        List<CourseDTO> courseDTOList = new ArrayList<>();
        Sort sortOrder = Sort.by("courseName").ascending();
        Page<Course> currentPage = courseRepository.findAll(PageRequest.of(page,size,sortOrder));

        return currentPage.stream().
                map(x -> new CourseDTO(x.getCourseName(),x.getCourseDescription()))
                .collect(Collectors.toList());
    }

    public List<CourseDTO> getData(PageRequestForCourse pageRequestForCourse){
        return getCourseDTOs(pageRequestForCourse.getCourses(entityManager));
    }

    public String updateName(Long courseId, String newName) {
        courseRepositoryCustom.updateCourseName(courseId,newName);
        return "done";
    }
}
