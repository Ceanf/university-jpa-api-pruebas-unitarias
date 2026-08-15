package com.example.universityjpa.dto;

public class DepartmentResponse {
    private Long id;
    private String name;
    private LecturerSummary[] lecturers;
    private StudentSummary[] students;

    public DepartmentResponse(Long id, String name, LecturerSummary[] lecturers, StudentSummary[] students) {
        this.id = id;
        this.name = name;
        this.lecturers = lecturers;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LecturerSummary[] getLecturers() {
        return lecturers;
    }

    public StudentSummary[] getStudents() {
        return students;
    }
}
