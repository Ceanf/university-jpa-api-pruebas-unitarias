package com.example.universityjpa.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DepartmentTest {

    @Test
    void deberiaCrearDepartmentConConstructorVacioYSetters() {
        Department department = new Department();

        department.setId(1L);
        department.setName("Ingenieria");

        assertEquals(1L, department.getId());
        assertEquals("Ingenieria", department.getName());
        assertNotNull(department.getLecturers());
        assertNotNull(department.getStudents());
        assertTrue(department.getLecturers().isEmpty());
        assertTrue(department.getStudents().isEmpty());
    }

    @Test
    void deberiaCrearDepartmentConConstructorParametrizado() {
        Department department = new Department("Ciencias");

        assertEquals("Ciencias", department.getName());
    }
}
