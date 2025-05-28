package com.security.demo.service;

import com.security.demo.entity.StudentEntity;
import com.security.demo.exception.NotFoundException;
import com.security.demo.mapper.StudentMapper;
import com.security.demo.model.Course;
import com.security.demo.model.CoursePatchDTO;
import com.security.demo.model.Student;
import com.security.demo.repositories.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class StudentService {
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository repository) {
        this.studentRepository = repository;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Student> getAllStudents() {
        log.info("Getting all students");
        //get all students
        return getAndMapAllStudents();
    }

    @PreAuthorize("hasRole('ADMIN')") //todo: add student's id check
    public List<Student> getStudentById(Long id) {
        log.info("Getting student by Id: {}", id);

        Optional<StudentEntity> studentOptional = studentRepository.findById(id);
        //map and return list of only one student or empty list
        return studentOptional.map(studentEntity -> List.of(
                StudentMapper.INSTANCE.toDto(
                        studentEntity
                )
        )).orElseThrow(() -> new NotFoundException("No student exist with provided id: " + id));
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostFilter("returnObject.department.equals(Department.valueOf(#department))")
    public List<Student> getTop50Students(String department) {
        log.info("Getting top 50 students");

        return getAndMapAllStudents()
                .stream()
//                .filter(student -> student.getDepartment().equals(Department.valueOf(department))) // -> can be replaced with PostFilter but will lower efficiency
                .sorted(Comparator.comparing(Student::getGPA).reversed())
                .limit(50)
                .toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteStudent(Long id) {
        try {
            if (null != id) {
                studentRepository.deleteById(id);
                return;
            }
            throw new BadRequestException("Null id is provided");
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Student createStudent(Student student) {
        if (null != student) {
            log.info("Student is not null");
            StudentEntity savedEntity = studentRepository.save(
                    StudentMapper.INSTANCE.toEntity(student)
            );

            log.info("Student is created");
            return StudentMapper.INSTANCE.toDto(savedEntity);

        }
        try {
            log.info("Student creation not done");
            throw new BadRequestException();
        } catch (BadRequestException e) {
            throw new RuntimeException("Student can't be null");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public Student updateStudent(Student student) {
        //validate the student exists
        try {
            Optional<StudentEntity> optionalStudent = studentRepository.findById(student.getId());
            if (optionalStudent.isPresent()) {
                StudentEntity savedEntity = studentRepository.save(
                        StudentMapper.INSTANCE.toEntity(student)
                );

                return StudentMapper.INSTANCE.toDto(savedEntity);
            }
            throw new NotFoundException("Student doesn't exist to be updated, insert student data instead");
        } catch (NotFoundException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')") //todo: add student's id check
    public Student updateStudentCourses(CoursePatchDTO dto) {
        //validate the student exists
        StudentEntity updatedStudentEntity; //used to store updated entity to be re-mapped to Student and returned
        try {
            Optional<StudentEntity> optionalStudent = studentRepository.findById(dto.getStudentId());
            if (optionalStudent.isPresent()) {
                Student mappedStudent = StudentMapper.INSTANCE.toDto(optionalStudent.get()); //get student from DB

                //get only valid (non-null) course IDs
                List<String> courseIds = dto.getCourses()
                        .stream()
                        .map(Course::getId)
                        .filter(Objects::nonNull)
                        .toList();
                if (courseIds.isEmpty()) {
                    throw new BadRequestException("No course valid ID found, no operation is done");
                }

                //operation is applied on list of courses
                switch (dto.getOp()) {
                    case ADD -> {
                        mappedStudent.getCourses().addAll(dto.getCourses());
                        //update student in database
                        updatedStudentEntity = studentRepository.save(StudentMapper.INSTANCE.toEntity(mappedStudent));
                    }
                    case REMOVE -> {
                        //loop through dto courses and remove them from student's data
                        for (Course course : dto.getCourses()) {
                            if (courseIds.contains(course.getId())) {
                                mappedStudent.getCourses().remove(course);
                            }
                        }

                        updatedStudentEntity = studentRepository.save(StudentMapper.INSTANCE.toEntity(mappedStudent)); //update student data
                    }
                    case UPDATE -> {
                        for (int i = 0; i < dto.getCourses().size(); i++) {
                            if (courseIds.contains(dto.getCourses().get(i).getId())) {
                                mappedStudent.getCourses().set(i, dto.getCourses().get(i));
                            }
                        }
                        updatedStudentEntity = studentRepository.save(StudentMapper.INSTANCE.toEntity(mappedStudent)); //update student data

                    }
                    default -> throw new BadRequestException("Unsupported operation");
                }
            } else {
                throw new BadRequestException("Student doesn't exist to be updated, insert student data instead");
            }
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }
        //re-map to Student object
        return StudentMapper.INSTANCE.toDto(updatedStudentEntity);
    }

    //get all students, stream on iterator and map results
    private List<Student> getAndMapAllStudents() {
        Iterable<StudentEntity> all = studentRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .map(StudentMapper.INSTANCE::toDto)
                .toList();
    }

    //this is a workaround to filter courses with PreFilter because as we mentioned the parameter must be a list
    @PreFilter("filterObject.courses.id != null && filterObject.courses.name")
    private List<Course> validateCourses(List<Course> courses) {
        try {
            log.info("Filtering and validating courses...");
            if (courses.isEmpty()) {
                throw new BadRequestException("All courses don't have id or name!");
            }
            return courses;
        } catch (BadRequestException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
