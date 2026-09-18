package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CreateRequests.LecturerRequest;
import com.example.universityjpa.dto.LecturerResponse;
import com.example.universityjpa.service.LecturerService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class LecturerControllerTest {

    @Mock
    private LecturerService lecturerService;

    @InjectMocks
    private LecturerController lecturerController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba que el controlador cree un docente correctamente
    @Test
    public void testCreateLecturer() {
        // Arrange
        LecturerRequest request = new LecturerRequest("Juan Perez");
        LecturerResponse expectedResponse =
                new LecturerResponse(1L, "Juan Perez", null, null);

        when(lecturerService.create("Juan Perez")).thenReturn(expectedResponse);

        // Act
        LecturerResponse result = lecturerController.create(request);

        // Assert
        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador retorne todos los docentes
    @Test
    public void testGetAllLecturers() {
        // Arrange
        LecturerResponse lecturer1 = new LecturerResponse(1L, "Juan Perez", null, null);
        LecturerResponse lecturer2 = new LecturerResponse(2L, "Maria Lopez", null, null);
        LecturerResponse[] expectedResponses = new LecturerResponse[]{lecturer1, lecturer2};

        when(lecturerService.getAll()).thenReturn(expectedResponses);

        // Act
        LecturerResponse[] result = lecturerController.getAll();

        // Assert
        assertEquals(2, result.length);
        assertEquals(lecturer1, result[0]);
        assertEquals(lecturer2, result[1]);
    }

    // Prueba que el controlador retorne un docente por su id
    @Test
    public void testGetLecturerById() {
        // Arrange
        Long id = 1L;
        LecturerResponse expectedResponse = new LecturerResponse(id, "Juan Perez", null, null);

        when(lecturerService.getById(id)).thenReturn(expectedResponse);

        // Act
        LecturerResponse result = lecturerController.getById(id);

        // Assert
        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador elimine un docente
    @Test
    public void testDeleteLecturer() {
        // Arrange
        Long id = 1L;
        doNothing().when(lecturerService).delete(id);

        // Act
        try {
            lecturerController.delete(id);
        } catch (Exception e) {
            fail("No se esperaba una excepcion: " + e.getMessage());
        }

        // Assert
        verify(lecturerService).delete(id);
    }

    // Prueba que el controlador asigne un curso al docente
    @Test
    public void testAssignCourse() {
        // Arrange
        Long lecturerId = 1L;
        Long courseId = 10L;
        LecturerResponse expectedResponse =
                new LecturerResponse(lecturerId, "Juan Perez", null, null);

        when(lecturerService.assignCourse(lecturerId, courseId)).thenReturn(expectedResponse);

        // Act
        LecturerResponse result = lecturerController.assignCourse(lecturerId, courseId);

        // Assert
        assertEquals(expectedResponse, result);
    }
}
