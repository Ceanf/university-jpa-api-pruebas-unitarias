package com.example.universityjpa.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentResponseTest {

    @Test
    void deberiaCrearStudentResponseCorrectamente() {
        DepartmentSummary department =
                new DepartmentSummary(1L, "Ingenieria");

        CourseSummary[] courses = {
                new CourseSummary(1L, "Programacion", "PRG001"),
                new CourseSummary(2L, "Bases de Datos", "BDD001")
        };

        StudentResponse response =
                new StudentResponse(10L, "Juan Perez", department, courses);

        assertEquals(10L, response.getId());
        assertEquals("Juan Perez", response.getName());
        assertEquals(department, response.getDepartment());
        assertArrayEquals(courses, response.getCourses());
    }

    @Test
    void deberiaPermitirDepartamentoNull() {
        CourseSummary[] courses = {
                new CourseSummary(1L, "Programacion", "PRG001")
        };

        StudentResponse response =
                new StudentResponse(10L, "Juan Perez", null, courses);

        assertEquals(10L, response.getId());
        assertEquals("Juan Perez", response.getName());
        assertNull(response.getDepartment());
        assertArrayEquals(courses, response.getCourses());
    }

    @Test
    void deberiaPermitirCursosNull() {
        DepartmentSummary department =
                new DepartmentSummary(1L, "Ingenieria");

        StudentResponse response =
                new StudentResponse(10L, "Juan Perez", department, null);

        assertEquals(10L, response.getId());
        assertEquals("Juan Perez", response.getName());
        assertEquals(department, response.getDepartment());
        assertNull(response.getCourses());
    }
}