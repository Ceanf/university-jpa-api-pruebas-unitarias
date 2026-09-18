package com.example.universityjpa.controller;

import com.example.universityjpa.dto.CourseResponse;
import com.example.universityjpa.dto.CreateRequests.CourseRequest;
import com.example.universityjpa.service.CourseService;

import org.junit.Before;
import org.junit.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;

public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCourse() {
        // Arrange
        CourseRequest request = new CourseRequest("Programacion", "PRG001");
        CourseResponse expectedResponse =
                new CourseResponse(1L, "Programacion", "PRG001", null, null);

        when(courseService.create("Programacion", "PRG001")).thenReturn(expectedResponse);

        // Act
        CourseResponse result = courseController.create(request);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testGetAllCourses() {
        // Arrange
        CourseResponse course1 = new CourseResponse(1L, "Programacion", "PRG001", null, null);
        CourseResponse course2 = new CourseResponse(2L, "Bases de Datos", "BDD001", null, null);
        CourseResponse[] expectedResponses = new CourseResponse[]{course1, course2};

        when(courseService.getAll()).thenReturn(expectedResponses);

        // Act
        CourseResponse[] result = courseController.getAll();

        // Assert
        assertEquals(2, result.length);
        assertEquals(course1, result[0]);
        assertEquals(course2, result[1]);
    }

    @Test
    public void testGetCourseById() {
        // Arrange
        Long id = 1L;
        CourseResponse expectedResponse = new CourseResponse(id, "Programacion", "PRG001", null, null);

        when(courseService.getById(id)).thenReturn(expectedResponse);

        // Act
        CourseResponse result = courseController.getById(id);

        // Assert
        assertEquals(expectedResponse, result);
    }

    @Test
    public void testDeleteCourse() {
        // Arrange
        Long id = 1L;
        doNothing().when(courseService).delete(id);

        // Act
        try {
            courseController.delete(id);
        } catch (Exception e) {
            fail("No se esperaba una excepcion: " + e.getMessage());
        }

        // Assert
        verify(courseService).delete(id);
    }

    @Test
    public void testEnrollStudent() {
        // Arrange
        Long courseId = 1L;
        Long studentId = 10L;
        CourseResponse expectedResponse =
                new CourseResponse(courseId, "Programacion", "PRG001", null, null);

        when(courseService.enrollStudent(courseId, studentId)).thenReturn(expectedResponse);

        // Act
        CourseResponse result = courseController.enrollStudent(courseId, studentId);

        // Assert
        assertEquals(expectedResponse, result);
    }
}
