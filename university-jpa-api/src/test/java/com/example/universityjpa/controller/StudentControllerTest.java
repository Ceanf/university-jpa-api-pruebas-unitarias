package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.StudentRequest;
import com.example.universityjpa.dto.StudentResponse;
import com.example.universityjpa.service.StudentService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba que el controlador cree un estudiante correctamente
    @Test
    public void testCreateStudent() {
        StudentRequest request = new StudentRequest("Ana Lopez");
        StudentResponse expectedResponse =
                new StudentResponse(1L, "Ana Lopez", null, null);

        when(studentService.create("Ana Lopez")).thenReturn(expectedResponse);

        StudentResponse result = studentController.create(request);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador retorne todos los estudiantes
    @Test
    public void testGetAllStudents() {
        StudentResponse student1 = new StudentResponse(1L, "Ana Lopez", null, null);
        StudentResponse student2 = new StudentResponse(2L, "Carlos Ruiz", null, null);
        StudentResponse[] expectedResponses = new StudentResponse[]{student1, student2};

        when(studentService.getAll()).thenReturn(expectedResponses);

        StudentResponse[] result = studentController.getAll();

        assertEquals(2, result.length);
        assertEquals(student1, result[0]);
        assertEquals(student2, result[1]);
    }

    // Prueba que el controlador retorne un estudiante por su id
    @Test
    public void testGetStudentById() {
        Long id = 1L;
        StudentResponse expectedResponse = new StudentResponse(id, "Ana Lopez", null, null);

        when(studentService.getById(id)).thenReturn(expectedResponse);

        StudentResponse result = studentController.getById(id);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador elimine un estudiante
    @Test
    public void testDeleteStudent() {
        Long id = 1L;
        doNothing().when(studentService).delete(id);

        studentController.delete(id);

        verify(studentService).delete(id);
    }
}