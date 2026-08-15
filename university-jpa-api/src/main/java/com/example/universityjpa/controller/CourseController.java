package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CourseResponse;
import com.example.universityjpa.dto.CreateRequests.CourseRequest;
import com.example.universityjpa.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CourseResponse create(@RequestBody CourseRequest request) {
        return courseService.create(request.title(), request.code());
    }

    @GetMapping
    public CourseResponse[] getAll() {
        return courseService.getAll();
    }

    @GetMapping("/{id}")
    public CourseResponse getById(@PathVariable Long id) {
        return courseService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }

    @PostMapping("/{courseId}/students/{studentId}")
    public CourseResponse enrollStudent(@PathVariable Long courseId, @PathVariable Long studentId) {
        return courseService.enrollStudent(courseId, studentId);
    }
}
