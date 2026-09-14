package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.StudentRequest;
import com.example.universityjpa.dto.StudentResponse;
import com.example.universityjpa.service.StudentService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    void create_deberiaCrearEstudiante() {
        StudentRequest request =
                new StudentRequest("Ana Lopez");

        StudentResponse expectedResponse =
                mock(StudentResponse.class);

        when(studentService.create("Ana Lopez"))
                .thenReturn(expectedResponse);

        StudentResponse response =
                studentController.create(request);

        assertSame(expectedResponse, response);

        verify(studentService).create("Ana Lopez");
    }

    @Test
    void getAll_deberiaRetornarTodosLosEstudiantes() {
        StudentResponse[] expectedResponses =
                new StudentResponse[0];

        when(studentService.getAll())
                .thenReturn(expectedResponses);

        StudentResponse[] responses =
                studentController.getAll();

        assertSame(expectedResponses, responses);

        verify(studentService).getAll();
    }

    @Test
    void getById_deberiaRetornarEstudiante() {
        StudentResponse expectedResponse =
                mock(StudentResponse.class);

        when(studentService.getById(1L))
                .thenReturn(expectedResponse);

        StudentResponse response =
                studentController.getById(1L);

        assertSame(expectedResponse, response);

        verify(studentService).getById(1L);
    }

    @Test
    void delete_deberiaEliminarEstudiante() {
        doNothing().when(studentService).delete(1L);

        studentController.delete(1L);

        verify(studentService).delete(1L);
    }
}