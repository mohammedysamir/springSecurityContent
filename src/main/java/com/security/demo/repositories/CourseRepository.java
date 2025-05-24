package com.security.demo.repositories;

import com.security.demo.entity.CourseEntity;
import com.security.demo.model.Course;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseRepository extends CrudRepository<CourseEntity, Long> {
    Optional<Course> findCourseByName(String name);
}
