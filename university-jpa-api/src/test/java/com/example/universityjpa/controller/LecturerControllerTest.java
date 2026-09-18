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
        LecturerRequest request = new LecturerRequest("Juan Perez");
        LecturerResponse expectedResponse =
                new LecturerResponse(1L, "Juan Perez", null, null);

        when(lecturerService.create("Juan Perez")).thenReturn(expectedResponse);

        LecturerResponse result = lecturerController.create(request);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador retorne todos los docentes
    @Test
    public void testGetAllLecturers() {
        LecturerResponse lecturer1 = new LecturerResponse(1L, "Juan Perez", null, null);
        LecturerResponse lecturer2 = new LecturerResponse(2L, "Maria Lopez", null, null);
        LecturerResponse[] expectedResponses = new LecturerResponse[]{lecturer1, lecturer2};

        when(lecturerService.getAll()).thenReturn(expectedResponses);

        LecturerResponse[] result = lecturerController.getAll();

        assertEquals(2, result.length);
        assertEquals(lecturer1, result[0]);
        assertEquals(lecturer2, result[1]);
    }

    // Prueba que el controlador retorne un docente por su id
    @Test
    public void testGetLecturerById() {
        Long id = 1L;
        LecturerResponse expectedResponse = new LecturerResponse(id, "Juan Perez", null, null);

        when(lecturerService.getById(id)).thenReturn(expectedResponse);

        LecturerResponse result = lecturerController.getById(id);

        assertEquals(expectedResponse, result);
    }

    // Prueba que el controlador elimine un docente
    @Test
    public void testDeleteLecturer() {
        Long id = 1L;
        doNothing().when(lecturerService).delete(id);

        lecturerController.delete(id);

        verify(lecturerService).delete(id);
    }

    // Prueba que el controlador asigne un curso al docente
    @Test
    public void testAssignCourse() {
        Long lecturerId = 1L;
        Long courseId = 10L;
        LecturerResponse expectedResponse =
                new LecturerResponse(lecturerId, "Juan Perez", null, null);

        when(lecturerService.assignCourse(lecturerId, courseId)).thenReturn(expectedResponse);

        LecturerResponse result = lecturerController.assignCourse(lecturerId, courseId);

        assertEquals(expectedResponse, result);
    }
}