package com.security.demo.model;

import org.apache.coyote.BadRequestException;

import java.util.List;


public class Course {
    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    //I created a constructor to add validation for CourseID
    public Course(String id, String name, List<String> instructors, Grade grade, String year){
        if (isCourseIdValid(id)) {
            this.id = id;
            this.name = name;
            this.instructors = instructors;
            this.grade = grade;
            this.year = year;
        } else {
            try {
                throw new BadRequestException();
            } catch (BadRequestException e) {
                throw new RuntimeException("Course ID must start with department name");
            }
        }
    }

    //validate that CourseId starts with department prefix
    private boolean isCourseIdValid(String id) {
        for (Department department : Department.values()) {
            if (id.startsWith(department.name())) {
                return true;
            }
        }
        return false;
    }


    String id;
    String name;
    List<String> instructors; //in the assignment I didn't mention it as a list just for simplicity
    Grade grade;
    String year;

}
