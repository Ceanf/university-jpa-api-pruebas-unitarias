package com.example.universityjpa.service;

import com.example.universityjpa.dto.CourseResponse;
import com.example.universityjpa.dto.LecturerSummary;
import com.example.universityjpa.dto.StudentSummary;
import com.example.universityjpa.entity.Course;
import com.example.universityjpa.entity.Student;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.CourseRepository;
import com.example.universityjpa.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository) {
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public CourseResponse create(String title, String code) {
        Course course = courseRepository.save(new Course(title, code));
        return toResponse(course);
    }

    @Transactional(readOnly = true)
    public CourseResponse[] getAll() {
        Course[] courses = courseRepository.findAll().toArray(new Course[0]);
        CourseResponse[] responses = new CourseResponse[courses.length];
        for (int i = 0; i < courses.length; i++) {
            responses[i] = toResponse(courses[i]);
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public CourseResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.delete(findEntity(id));
    }

    // Course <-> Students (enrollment)
    @Transactional
    public CourseResponse enrollStudent(Long courseId, Long studentId) {
        Course course = findEntity(courseId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
        course.getStudents().add(student);
        student.getCourses().add(course);
        courseRepository.save(course);
        return toResponse(course);
    }

    private Course findEntity(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    private CourseResponse toResponse(Course course) {
        LecturerSummary lecturer = course.getLecturer() == null
                ? null
                : new LecturerSummary(course.getLecturer().getId(), course.getLecturer().getName());

        StudentSummary[] students = course.getStudents().stream()
                .map(s -> new StudentSummary(s.getId(), s.getName()))
                .toArray(StudentSummary[]::new);

        return new CourseResponse(course.getId(), course.getTitle(), course.getCode(), lecturer, students);
    }
}
