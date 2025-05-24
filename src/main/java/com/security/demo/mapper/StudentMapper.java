package com.security.demo.mapper;

import com.security.demo.entity.StudentEntity;
import com.security.demo.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {
    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);

    Student toDto(StudentEntity studentEntity);

    StudentEntity toEntity(Student student);
}
