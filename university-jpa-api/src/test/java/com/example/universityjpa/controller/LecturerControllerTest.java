package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.LecturerRequest;
import com.example.universityjpa.dto.LecturerResponse;
import com.example.universityjpa.service.LecturerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturerControllerTest {

    @Mock
    private LecturerService lecturerService;

    @InjectMocks
    private LecturerController lecturerController;

    @Test
    void create_deberiaCrearDocente() {
        LecturerRequest request =
                new LecturerRequest("Juan Perez");

        LecturerResponse expectedResponse =
                mock(LecturerResponse.class);

        when(lecturerService.create("Juan Perez"))
                .thenReturn(expectedResponse);

        LecturerResponse response =
                lecturerController.create(request);

        assertSame(expectedResponse, response);

        verify(lecturerService).create("Juan Perez");
    }

    @Test
    void getAll_deberiaRetornarTodosLosDocentes() {
        LecturerResponse[] expectedResponses =
                new LecturerResponse[0];

        when(lecturerService.getAll())
                .thenReturn(expectedResponses);

        LecturerResponse[] responses =
                lecturerController.getAll();

        assertSame(expectedResponses, responses);

        verify(lecturerService).getAll();
    }

    @Test
    void getById_deberiaRetornarDocente() {
        LecturerResponse expectedResponse =
                mock(LecturerResponse.class);

        when(lecturerService.getById(1L))
                .thenReturn(expectedResponse);

        LecturerResponse response =
                lecturerController.getById(1L);

        assertSame(expectedResponse, response);

        verify(lecturerService).getById(1L);
    }

    @Test
    void delete_deberiaEliminarDocente() {
        doNothing().when(lecturerService).delete(1L);

        lecturerController.delete(1L);

        verify(lecturerService).delete(1L);
    }

    @Test
    void assignCourse_deberiaAsignarCurso() {
        LecturerResponse expectedResponse =
                mock(LecturerResponse.class);

        when(lecturerService.assignCourse(1L, 10L))
                .thenReturn(expectedResponse);

        LecturerResponse response =
                lecturerController.assignCourse(1L, 10L);

        assertSame(expectedResponse, response);

        verify(lecturerService).assignCourse(1L, 10L);
    }
}