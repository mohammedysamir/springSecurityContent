package com.security.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class Student {
    Long id;
    String name;
    Date dateOfBirth;
    Date joinDate;
    Date graduateDate;
    Float GPA;
    Department department;
    List<Course> courses;
}
