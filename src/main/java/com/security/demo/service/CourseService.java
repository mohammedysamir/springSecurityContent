package com.security.demo.service;

import com.security.demo.entity.CourseEntity;
import com.security.demo.exception.NotFoundException;
import com.security.demo.mapper.CourseMapper;
import com.security.demo.model.Course;
import com.security.demo.repositories.CourseRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Course createCourse(Course course) {
        CourseEntity courseEntity = courseRepository.save(CourseMapper.INSTANCE.toEntity(course));
        return course;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<Course> getAllCourses() {
        return getAndMapAllCourses();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Course getCourseById(String id) {
        return courseRepository
                .findById(id)
                .map(CourseMapper.INSTANCE::toDto)
                .orElseThrow(() -> new NotFoundException("No student exist with provided id: " + id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCourse(String id) {
        try {
            if (null != id && !id.isEmpty()) {
                courseRepository.deleteById(id);
                return;
            }
            throw new BadRequestException("Null or Empty course id is provided");
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Course updateCourse(Course course) {
        //validate the course exists
        try {
            Optional<CourseEntity> optionalStudent = courseRepository.findById(course.getId());
            if (optionalStudent.isPresent()) {
                CourseEntity savedEntity = courseRepository.save(
                        CourseMapper.INSTANCE.toEntity(course)
                );

                return CourseMapper.INSTANCE.toDto(savedEntity);
            }
            throw new NotFoundException("Course doesn't exist to be updated, insert course data instead");
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    //get all students, stream on iterator and map results
    private List<Course> getAndMapAllCourses() {
        Iterable<CourseEntity> all = courseRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(CourseMapper.INSTANCE::toDto)
                .toList();
    }
}
