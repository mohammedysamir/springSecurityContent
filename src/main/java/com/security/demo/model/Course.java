package com.security.demo.model;

import lombok.Getter;
import org.apache.coyote.BadRequestException;

import java.util.List;

@Getter
public class Course {
    //I created a constructor to add validation for CourseID
    public Course(String id, String name, List<String> instructors, Grade grade, String year) throws BadRequestException {
        if (isCourseIdValid(id)) {
            this.id = id;
            this.name = name;
            this.instructors = instructors;
            this.grade = grade;
            this.year = year;
        } else {
            throw new BadRequestException("Course ID must start with department name");
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
