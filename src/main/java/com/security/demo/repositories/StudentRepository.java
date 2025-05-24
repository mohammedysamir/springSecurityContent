package com.security.demo.repositories;

import com.security.demo.entity.StudentEntity;
import com.security.demo.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

}
