package com.example.universityjpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LecturerTest {

    @Test
    void deberiaCrearLecturerConConstructorVacioYSetters() {
        Lecturer lecturer = new Lecturer();

        lecturer.setId(1L);
        lecturer.setName("Carlos Perez");

        assertEquals(1L, lecturer.getId());
        assertEquals("Carlos Perez", lecturer.getName());
        assertNull(lecturer.getDepartment());
        assertNotNull(lecturer.getCourses());
        assertTrue(lecturer.getCourses().isEmpty());
    }

    @Test
    void deberiaCrearLecturerConConstructorParametrizado() {
        Lecturer lecturer = new Lecturer("Ana Torres");

        assertEquals("Ana Torres", lecturer.getName());
    }

    @Test
    void deberiaAsignarDepartment() {
        Lecturer lecturer = new Lecturer("Ana Torres");
        Department department = new Department("Ingenieria");

        lecturer.setDepartment(department);

        assertEquals(department, lecturer.getDepartment());
    }
}
