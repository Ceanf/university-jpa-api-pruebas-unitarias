package com.example.universityjpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @Test
    void deberiaCrearCourseConConstructorVacioYSetters() {
        Course course = new Course();

        course.setId(1L);
        course.setTitle("Bases de Datos");
        course.setCode("BD-101");

        assertEquals(1L, course.getId());
        assertEquals("Bases de Datos", course.getTitle());
        assertEquals("BD-101", course.getCode());
        assertNull(course.getLecturer());
        assertNotNull(course.getStudents());
        assertTrue(course.getStudents().isEmpty());
    }

    @Test
    void deberiaCrearCourseConConstructorParametrizado() {
        Course course = new Course("Programacion I", "PROG-100");

        assertEquals("Programacion I", course.getTitle());
        assertEquals("PROG-100", course.getCode());
    }

    @Test
    void deberiaAsignarLecturer() {
        Course course = new Course("Redes", "RED-200");
        Lecturer lecturer = new Lecturer("Ana Torres");

        course.setLecturer(lecturer);

        assertEquals(lecturer, course.getLecturer());
    }
}
