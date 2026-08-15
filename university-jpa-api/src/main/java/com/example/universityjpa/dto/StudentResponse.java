package com.example.universityjpa.dto;

public class StudentResponse {
    private Long id;
    private String name;
    private DepartmentSummary department;
    private CourseSummary[] courses;

    public StudentResponse(Long id, String name, DepartmentSummary department, CourseSummary[] courses) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.courses = courses;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public DepartmentSummary getDepartment() {
        return department;
    }

    public CourseSummary[] getCourses() {
        return courses;
    }
}
