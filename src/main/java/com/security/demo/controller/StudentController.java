package com.security.demo.controller;

import com.security.demo.model.CoursePatchDTO;
import com.security.demo.model.Student;
import com.security.demo.service.StudentService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/students")
    public List<Student> getStudents(@RequestParam(required = false) Long id) {
        if (null != id) {
            return List.of();
        }
        return List.of();
    }

    @GetMapping("/students/top50")
    public List<Student> getTop50Students() {
        return List.of();
    }


    @PostMapping("/students")
    public Student createStudents(@RequestBody Student student) {
        return new Student();
    }


    @PostMapping("/students")
    public Student patchStudentCourse(@RequestBody CoursePatchDTO dto) {
        return new Student();
    }

    @DeleteMapping("/students/{id}")
    public Student deleteStudent(@PathParam("id") Long id) {
        return new Student();
    }
}
