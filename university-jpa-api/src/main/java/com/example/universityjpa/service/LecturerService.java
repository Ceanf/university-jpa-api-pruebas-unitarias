package com.example.universityjpa.service;

import com.example.universityjpa.dto.CourseSummary;
import com.example.universityjpa.dto.DepartmentSummary;
import com.example.universityjpa.dto.LecturerResponse;
import com.example.universityjpa.entity.Course;
import com.example.universityjpa.entity.Lecturer;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.CourseRepository;
import com.example.universityjpa.repository.LecturerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;

    public LecturerService(LecturerRepository lecturerRepository, CourseRepository courseRepository) {
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public LecturerResponse create(String name) {
        Lecturer lecturer = lecturerRepository.save(new Lecturer(name));
        return toResponse(lecturer);
    }

    @Transactional(readOnly = true)
    public LecturerResponse[] getAll() {
        Lecturer[] lecturers = lecturerRepository.findAll().toArray(new Lecturer[0]);
        LecturerResponse[] responses = new LecturerResponse[lecturers.length];
        for (int i = 0; i < lecturers.length; i++) {
            responses[i] = toResponse(lecturers[i]);
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public LecturerResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        lecturerRepository.delete(findEntity(id));
    }

    // Lecturer -> Courses (teaches)
    @Transactional
    public LecturerResponse assignCourse(Long lecturerId, Long courseId) {
        Lecturer lecturer = findEntity(lecturerId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + courseId));
        course.setLecturer(lecturer);
        lecturer.getCourses().add(course);
        courseRepository.save(course);
        return toResponse(lecturer);
    }

    private Lecturer findEntity(Long id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + id));
    }

    private LecturerResponse toResponse(Lecturer lecturer) {
        DepartmentSummary department = lecturer.getDepartment() == null
                ? null
                : new DepartmentSummary(lecturer.getDepartment().getId(), lecturer.getDepartment().getName());

        CourseSummary[] courses = lecturer.getCourses().stream()
                .map(c -> new CourseSummary(c.getId(), c.getTitle(), c.getCode()))
                .toArray(CourseSummary[]::new);

        return new LecturerResponse(lecturer.getId(), lecturer.getName(), department, courses);
    }
}
