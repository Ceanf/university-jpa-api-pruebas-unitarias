package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CourseResponse;
import com.example.universityjpa.dto.CreateRequests.CourseRequest;
import com.example.universityjpa.service.CourseService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Test
    void create_deberiaCrearCurso() {
        CourseRequest request =
                new CourseRequest("Programacion", "PRG001");

        CourseResponse expectedResponse = mock(CourseResponse.class);

        when(courseService.create("Programacion", "PRG001"))
                .thenReturn(expectedResponse);

        CourseResponse response =
                courseController.create(request);

        assertSame(expectedResponse, response);

        verify(courseService).create("Programacion", "PRG001");
    }

    @Test
    void getAll_deberiaRetornarTodosLosCursos() {
        CourseResponse[] expectedResponses =
                new CourseResponse[0];

        when(courseService.getAll())
                .thenReturn(expectedResponses);

        CourseResponse[] responses =
                courseController.getAll();

        assertSame(expectedResponses, responses);

        verify(courseService).getAll();
    }

    @Test
    void getById_deberiaRetornarCurso() {
        CourseResponse expectedResponse =
                mock(CourseResponse.class);

        when(courseService.getById(1L))
                .thenReturn(expectedResponse);

        CourseResponse response =
                courseController.getById(1L);

        assertSame(expectedResponse, response);

        verify(courseService).getById(1L);
    }

    @Test
    void delete_deberiaEliminarCurso() {
        doNothing().when(courseService).delete(1L);

        courseController.delete(1L);

        verify(courseService).delete(1L);
    }

    @Test
    void enrollStudent_deberiaMatricularEstudiante() {
        CourseResponse expectedResponse =
                mock(CourseResponse.class);

        when(courseService.enrollStudent(1L, 10L))
                .thenReturn(expectedResponse);

        CourseResponse response =
                courseController.enrollStudent(1L, 10L);

        assertSame(expectedResponse, response);

        verify(courseService).enrollStudent(1L, 10L);
    }
}