package com.example.FirstApp;

import com.example.FirstApp.entity.Course;
import com.example.FirstApp.service.CourseService;
import com.example.FirstApp.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FirstAppApplicationTests {

	@Autowired
	CourseService courseService;

	@Autowired
	StudentService studentService;

	@Test
	void contextLoads() {
	}

	@Test
	void courseList(){
		Course course = courseService.findCourseById(1l);
		System.out.println(course.getCourseName());
		System.out.println(course.getCourseDescription());

	}

}
