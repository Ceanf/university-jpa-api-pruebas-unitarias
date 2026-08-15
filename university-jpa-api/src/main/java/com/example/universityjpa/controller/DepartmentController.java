package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.DepartmentRequest;
import com.example.universityjpa.dto.DepartmentResponse;
import com.example.universityjpa.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DepartmentResponse create(@RequestBody DepartmentRequest request) {
        return departmentService.create(request.name());
    }

    @GetMapping
    public DepartmentResponse[] getAll() {
        return departmentService.getAll();
    }

    @GetMapping("/{id}")
    public DepartmentResponse getById(@PathVariable Long id) {
        return departmentService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        departmentService.delete(id);
    }

    @PostMapping("/{departmentId}/lecturers/{lecturerId}")
    public DepartmentResponse addLecturer(@PathVariable Long departmentId, @PathVariable Long lecturerId) {
        return departmentService.addLecturer(departmentId, lecturerId);
    }

    @PostMapping("/{departmentId}/students/{studentId}")
    public DepartmentResponse addStudent(@PathVariable Long departmentId, @PathVariable Long studentId) {
        return departmentService.addStudent(departmentId, studentId);
    }
}
