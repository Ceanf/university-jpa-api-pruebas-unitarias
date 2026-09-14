package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.DepartmentRequest;
import com.example.universityjpa.dto.DepartmentResponse;
import com.example.universityjpa.service.DepartmentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentControllerTest {

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @Test
    void create_deberiaCrearDepartamento() {
        DepartmentRequest request =
                new DepartmentRequest("Ingenieria");

        DepartmentResponse expectedResponse =
                mock(DepartmentResponse.class);

        when(departmentService.create("Ingenieria"))
                .thenReturn(expectedResponse);

        DepartmentResponse response =
                departmentController.create(request);

        assertSame(expectedResponse, response);

        verify(departmentService).create("Ingenieria");
    }

    @Test
    void getAll_deberiaRetornarTodosLosDepartamentos() {
        DepartmentResponse[] expectedResponses =
                new DepartmentResponse[0];

        when(departmentService.getAll())
                .thenReturn(expectedResponses);

        DepartmentResponse[] responses =
                departmentController.getAll();

        assertSame(expectedResponses, responses);

        verify(departmentService).getAll();
    }

    @Test
    void getById_deberiaRetornarDepartamento() {
        DepartmentResponse expectedResponse =
                mock(DepartmentResponse.class);

        when(departmentService.getById(1L))
                .thenReturn(expectedResponse);

        DepartmentResponse response =
                departmentController.getById(1L);

        assertSame(expectedResponse, response);

        verify(departmentService).getById(1L);
    }

    @Test
    void delete_deberiaEliminarDepartamento() {
        doNothing().when(departmentService).delete(1L);

        departmentController.delete(1L);

        verify(departmentService).delete(1L);
    }

    @Test
    void addLecturer_deberiaAgregarDocente() {
        DepartmentResponse expectedResponse =
                mock(DepartmentResponse.class);

        when(departmentService.addLecturer(1L, 10L))
                .thenReturn(expectedResponse);

        DepartmentResponse response =
                departmentController.addLecturer(1L, 10L);

        assertSame(expectedResponse, response);

        verify(departmentService).addLecturer(1L, 10L);
    }

    @Test
    void addStudent_deberiaAgregarEstudiante() {
        DepartmentResponse expectedResponse =
                mock(DepartmentResponse.class);

        when(departmentService.addStudent(1L, 20L))
                .thenReturn(expectedResponse);

        DepartmentResponse response =
                departmentController.addStudent(1L, 20L);

        assertSame(expectedResponse, response);

        verify(departmentService).addStudent(1L, 20L);
    }
}