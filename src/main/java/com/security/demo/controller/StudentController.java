package com.security.demo.controller;

import com.security.demo.model.CoursePatchDTO;
import com.security.demo.model.Student;
import com.security.demo.service.StudentService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@Slf4j
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<List<Student>> getStudents(@RequestParam(required = false) Long id) {
        List<Student> studentList;
        if (null != id) {
            studentList = studentService.getStudentById(id);
        } else {
            studentList = studentService.getAllStudents();
        }
        return new ResponseEntity<>(studentList, HttpStatus.OK);
    }

    @GetMapping("/top50")
    public ResponseEntity<List<Student>> getTop50Students(@RequestParam String department) {
        return new ResponseEntity<>(studentService.getTop50Students(department), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.createStudent(student), HttpStatus.OK);
    }


    @PatchMapping
    public ResponseEntity<Student> patchStudent(@RequestBody Student student) {
        return new ResponseEntity<>(studentService.updateStudent(student), HttpStatus.OK);
    }


    @PatchMapping("/courses")
    public ResponseEntity<Student> patchStudentCourse(@RequestBody CoursePatchDTO dto) {
        return new ResponseEntity<>(studentService.updateStudentCourses(dto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathParam("id") Long id) {
        studentService.deleteStudent(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
