package com.example.universityjpa.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentResponseTest {

    @Test
    void deberiaCrearDepartmentResponseCorrectamente() {
        LecturerSummary[] lecturers = {
                new LecturerSummary(1L, "Carlos Perez")
        };

        StudentSummary[] students = {
                new StudentSummary(2L, "Juan Lopez")
        };

        DepartmentResponse response =
                new DepartmentResponse(
                        10L,
                        "Ingenieria de Sistemas",
                        lecturers,
                        students
                );

        assertEquals(10L, response.getId());
        assertEquals("Ingenieria de Sistemas", response.getName());
        assertArrayEquals(lecturers, response.getLecturers());
        assertArrayEquals(students, response.getStudents());
    }

    @Test
    void deberiaPermitirArraysNull() {
        DepartmentResponse response =
                new DepartmentResponse(
                        10L,
                        "Ingenieria de Sistemas",
                        null,
                        null
                );

        assertEquals(10L, response.getId());
        assertEquals("Ingenieria de Sistemas", response.getName());
        assertNull(response.getLecturers());
        assertNull(response.getStudents());
    }
}