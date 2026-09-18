package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.DepartmentRequest;
import com.example.universityjpa.dto.DepartmentResponse;
import com.example.universityjpa.service.DepartmentService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba que el controlador cree un departamento correctamente
    @Test
    public void testCreateDepartment() {
        DepartmentRequest request = new DepartmentRequest("Ingenieria");
        DepartmentResponse expectedResponse =
                new DepartmentResponse(1L, "Ingenieria", null, null);

        when(departmentService.create("Ingenieria")).thenReturn(expectedResponse);

        DepartmentResponse result = departmentController.create(request);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador retorne todos los departamentos
    @Test
    public void testGetAllDepartments() {
        DepartmentResponse department1 = new DepartmentResponse(1L, "Ingenieria", null, null);
        DepartmentResponse department2 = new DepartmentResponse(2L, "Ciencias", null, null);
        DepartmentResponse[] expectedResponses = new DepartmentResponse[]{department1, department2};

        when(departmentService.getAll()).thenReturn(expectedResponses);

        DepartmentResponse[] result = departmentController.getAll();

        assertEquals(2, result.length);
        assertEquals(department1, result[0]);
        assertEquals(department2, result[1]);
    }

    // Prueba que el controlador retorne un departamento por su id
    @Test
    public void testGetDepartmentById() {
        Long id = 1L;
        DepartmentResponse expectedResponse = new DepartmentResponse(id, "Ingenieria", null, null);

        when(departmentService.getById(id)).thenReturn(expectedResponse);

        DepartmentResponse result = departmentController.getById(id);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador elimine un departamento
    @Test
    public void testDeleteDepartment() {
        Long id = 1L;
        doNothing().when(departmentService).delete(id);

        departmentController.delete(id);

        verify(departmentService).delete(id);
    }

    // Prueba que el controlador agregue un docente al departamento
    @Test
    public void testAddLecturer() {
        Long departmentId = 1L;
        Long lecturerId = 10L;
        DepartmentResponse expectedResponse =
                new DepartmentResponse(departmentId, "Ingenieria", null, null);

        when(departmentService.addLecturer(departmentId, lecturerId)).thenReturn(expectedResponse);

        DepartmentResponse result = departmentController.addLecturer(departmentId, lecturerId);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador agregue un estudiante al departamento
    @Test
    public void testAddStudent() {
        Long departmentId = 1L;
        Long studentId = 20L;
        DepartmentResponse expectedResponse =
                new DepartmentResponse(departmentId, "Ingenieria", null, null);

        when(departmentService.addStudent(departmentId, studentId)).thenReturn(expectedResponse);

        DepartmentResponse result = departmentController.addStudent(departmentId, studentId);

        assertEquals(expectedResponse, result);
    }
}