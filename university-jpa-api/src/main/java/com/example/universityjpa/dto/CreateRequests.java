package com.example.universityjpa.dto;

public class CreateRequests {

    public record DepartmentRequest(String name) {
    }

    public record LecturerRequest(String name) {
    }

    public record CourseRequest(String title, String code) {
    }

    public record StudentRequest(String name) {
    }
}
