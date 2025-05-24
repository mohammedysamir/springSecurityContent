package com.security.demo.entity;

import com.security.demo.model.Grade;
import com.security.demo.model.Student;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "courses")
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ElementCollection
    private List<String> instructors;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private String year;
    @ManyToMany(mappedBy = "courses")
    List<Student> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<String> instructors) {
        this.instructors = instructors;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public CourseEntity(Long id, String name, List<String> instructors, Grade grade, String year, List<Student> students) {
        this.id = id;
        this.name = name;
        this.instructors = instructors;
        this.grade = grade;
        this.year = year;
        this.students = students;
    }
}
