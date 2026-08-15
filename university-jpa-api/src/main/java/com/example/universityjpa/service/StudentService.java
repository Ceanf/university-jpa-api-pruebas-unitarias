package com.example.universityjpa.service;

import com.example.universityjpa.dto.CourseSummary;
import com.example.universityjpa.dto.DepartmentSummary;
import com.example.universityjpa.dto.StudentResponse;
import com.example.universityjpa.entity.Student;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Transactional
    public StudentResponse create(String name) {
        Student student = studentRepository.save(new Student(name));
        return toResponse(student);
    }

    @Transactional(readOnly = true)
    public StudentResponse[] getAll() {
        Student[] students = studentRepository.findAll().toArray(new Student[0]);
        StudentResponse[] responses = new StudentResponse[students.length];
        for (int i = 0; i < students.length; i++) {
            responses[i] = toResponse(students[i]);
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public StudentResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.delete(findEntity(id));
    }

    private Student findEntity(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + id));
    }

    private StudentResponse toResponse(Student student) {
        DepartmentSummary department = student.getDepartment() == null
                ? null
                : new DepartmentSummary(student.getDepartment().getId(), student.getDepartment().getName());

        CourseSummary[] courses = student.getCourses().stream()
                .map(c -> new CourseSummary(c.getId(), c.getTitle(), c.getCode()))
                .toArray(CourseSummary[]::new);

        return new StudentResponse(student.getId(), student.getName(), department, courses);
    }
}
