package com.example.universityjpa.dto;

public class CourseResponse {
    private Long id;
    private String title;
    private String code;
    private LecturerSummary lecturer;
    private StudentSummary[] students;

    public CourseResponse(Long id, String title, String code, LecturerSummary lecturer, StudentSummary[] students) {
        this.id = id;
        this.title = title;
        this.code = code;
        this.lecturer = lecturer;
        this.students = students;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCode() {
        return code;
    }

    public LecturerSummary getLecturer() {
        return lecturer;
    }

    public StudentSummary[] getStudents() {
        return students;
    }
}
