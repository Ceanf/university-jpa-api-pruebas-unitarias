package com.example.universityjpa.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LecturerResponseTest {

    @Test
    void deberiaCrearLecturerResponseCorrectamente() {
        DepartmentSummary department =
                new DepartmentSummary(1L, "Ingenieria de Sistemas");

        CourseSummary[] courses = {
                new CourseSummary(1L, "Programacion", "PRG001"),
                new CourseSummary(2L, "Bases de Datos", "BDD001")
        };

        LecturerResponse response =
                new LecturerResponse(
                        10L,
                        "Carlos Perez",
                        department,
                        courses
                );

        assertEquals(10L, response.getId());
        assertEquals("Carlos Perez", response.getName());
        assertEquals(department, response.getDepartment());
        assertArrayEquals(courses, response.getCourses());
    }

    @Test
    void deberiaPermitirDepartamentoNull() {
        CourseSummary[] courses = {
                new CourseSummary(1L, "Programacion", "PRG001")
        };

        LecturerResponse response =
                new LecturerResponse(
                        10L,
                        "Carlos Perez",
                        null,
                        courses
                );

        assertEquals(10L, response.getId());
        assertEquals("Carlos Perez", response.getName());
        assertNull(response.getDepartment());
        assertArrayEquals(courses, response.getCourses());
    }

    @Test
    void deberiaPermitirCursosNull() {
        DepartmentSummary department =
                new DepartmentSummary(1L, "Ingenieria de Sistemas");

        LecturerResponse response =
                new LecturerResponse(
                        10L,
                        "Carlos Perez",
                        department,
                        null
                );

        assertEquals(10L, response.getId());
        assertEquals("Carlos Perez", response.getName());
        assertEquals(department, response.getDepartment());
        assertNull(response.getCourses());
    }
}