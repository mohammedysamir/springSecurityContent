package com.security.demo.model;

import java.util.List;

public class CoursePatchDTO {
    Operations op;
    Long studentId;
    List<Course> courses;

    public Operations getOp() {
        return op;
    }

    public Long getStudentId() {
        return studentId;
    }

    public List<Course> getCourses() {
        return courses;
    }
}
