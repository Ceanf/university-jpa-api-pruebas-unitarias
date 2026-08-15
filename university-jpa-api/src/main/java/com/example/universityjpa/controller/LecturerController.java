package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.LecturerRequest;
import com.example.universityjpa.dto.LecturerResponse;
import com.example.universityjpa.service.LecturerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;

    public LecturerController(LecturerService lecturerService) {
        this.lecturerService = lecturerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LecturerResponse create(@RequestBody LecturerRequest request) {
        return lecturerService.create(request.name());
    }

    @GetMapping
    public LecturerResponse[] getAll() {
        return lecturerService.getAll();
    }

    @GetMapping("/{id}")
    public LecturerResponse getById(@PathVariable Long id) {
        return lecturerService.getById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        lecturerService.delete(id);
    }

    @PostMapping("/{lecturerId}/courses/{courseId}")
    public LecturerResponse assignCourse(@PathVariable Long lecturerId, @PathVariable Long courseId) {
        return lecturerService.assignCourse(lecturerId, courseId);
    }
}
