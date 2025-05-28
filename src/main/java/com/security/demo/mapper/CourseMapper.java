package com.security.demo.mapper;

import com.security.demo.entity.CourseEntity;
import com.security.demo.model.Course;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CourseMapper {
    CourseMapper INSTANCE = Mappers.getMapper(CourseMapper.class);

    Course toDto(CourseEntity courseEntity);

    CourseEntity toEntity(Course course);
}
