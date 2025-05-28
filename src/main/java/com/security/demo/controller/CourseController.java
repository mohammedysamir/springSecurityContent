package com.security.demo.controller;

import com.security.demo.model.Course;
import com.security.demo.service.CourseService;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/courses")
@RestController
public class CourseController {
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Course> getCourseById(@PathParam("courseId") String courseId) {
        return new ResponseEntity<>(courseService.getCourseById(courseId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(Course course) {
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<Course> patchCourse(Course course) {
        return new ResponseEntity<>(courseService.updateCourse(course), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathParam("courseId") String courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
