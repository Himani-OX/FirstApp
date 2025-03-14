package com.example.FirstApp.contoller;


import com.example.FirstApp.DTO.CourseDTO;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
@Data
@Slf4j
public class DemoController {

    @PostMapping("/")
    public String get(@RequestBody CourseDTO courseDTO){
        log.info(courseDTO.getCourseName());
        return courseDTO.getCourseName();
    }
}
