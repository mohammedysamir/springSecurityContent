package com.security.demo.repositories;

import com.security.demo.entity.CourseEntity;
import com.security.demo.model.Course;
import com.security.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

}
