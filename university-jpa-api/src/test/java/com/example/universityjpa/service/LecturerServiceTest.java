package com.example.universityjpa.service;

import com.example.universityjpa.entity.Course;
import com.example.universityjpa.entity.Department;
import com.example.universityjpa.entity.Lecturer;
import com.example.universityjpa.dto.LecturerResponse;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.CourseRepository;
import com.example.universityjpa.repository.LecturerRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LecturerServiceTest {

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private LecturerService lecturerService;

    @Test
    void create_deberiaCrearDocenteCorrectamente() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        when(lecturerRepository.save(any(Lecturer.class)))
                .thenReturn(lecturer);

        // Act
        LecturerResponse response =
                lecturerService.create("Juan Perez");

        // Assert
        assertNotNull(response);

        verify(lecturerRepository).save(any(Lecturer.class));
    }

    @Test
    void getAll_deberiaRetornarTodosLosDocentes() {
        // Arrange
        Lecturer lecturer1 = new Lecturer("Juan Perez");
        lecturer1.setId(1L);

        Lecturer lecturer2 = new Lecturer("Ana Lopez");
        lecturer2.setId(2L);

        when(lecturerRepository.findAll())
                .thenReturn(List.of(lecturer1, lecturer2));

        // Act
        LecturerResponse[] responses =
                lecturerService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.length);

        verify(lecturerRepository).findAll();
    }

    @Test
    void getAll_deberiaRetornarArregloVacio() {
        // Arrange
        when(lecturerRepository.findAll())
                .thenReturn(List.of());

        // Act
        LecturerResponse[] responses =
                lecturerService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(0, responses.length);

        verify(lecturerRepository).findAll();
    }

    @Test
    void getById_deberiaRetornarDocenteCuandoExiste() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        when(lecturerRepository.findById(1L))
                .thenReturn(Optional.of(lecturer));

        // Act
        LecturerResponse response =
                lecturerService.getById(1L);

        // Assert
        assertNotNull(response);

        verify(lecturerRepository).findById(1L);
    }

    @Test
    void getById_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(lecturerRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> lecturerService.getById(99L)
        );

        verify(lecturerRepository).findById(99L);
    }

    @Test
    void delete_deberiaEliminarDocenteCuandoExiste() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        when(lecturerRepository.findById(1L))
                .thenReturn(Optional.of(lecturer));

        // Act
        assertDoesNotThrow(() -> lecturerService.delete(1L));

        // Assert
        verify(lecturerRepository).findById(1L);
        verify(lecturerRepository).delete(lecturer);
    }

    @Test
    void delete_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(lecturerRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> lecturerService.delete(99L)
        );

        verify(lecturerRepository, never())
                .delete(any(Lecturer.class));
    }

    @Test
    void assignCourse_deberiaAsignarCursoAlDocente() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        Course course = new Course("Programacion", "PRG001");
        course.setId(10L);

        when(lecturerRepository.findById(1L))
                .thenReturn(Optional.of(lecturer));

        when(courseRepository.findById(10L))
                .thenReturn(Optional.of(course));

        // Act
        LecturerResponse response =
                lecturerService.assignCourse(1L, 10L);

        // Assert
        assertNotNull(response);
        assertSame(lecturer, course.getLecturer());
        assertTrue(lecturer.getCourses().contains(course));

        verify(courseRepository).save(course);
        verify(lecturerRepository).findById(1L);
        verify(courseRepository).findById(10L);
    }

    @Test
    void assignCourse_deberiaLanzarExcepcionSiNoExisteElDocente() {
        // Arrange
        when(lecturerRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> lecturerService.assignCourse(99L, 10L)
        );

        verify(courseRepository, never())
                .findById(anyLong());

        verify(courseRepository, never())
                .save(any(Course.class));
    }

    @Test
    void assignCourse_deberiaLanzarExcepcionSiNoExisteElCurso() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        when(lecturerRepository.findById(1L))
                .thenReturn(Optional.of(lecturer));

        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> lecturerService.assignCourse(1L, 99L)
        );

        verify(courseRepository).findById(99L);

        verify(courseRepository, never())
                .save(any(Course.class));
    }

    @Test
    void getById_deberiaConvertirDepartamentoYCurso() {
        // Arrange
        Lecturer lecturer = new Lecturer("Juan Perez");
        lecturer.setId(1L);

        Department department = new Department("Ingenieria");
        department.setId(5L);

        Course course = new Course("Programacion", "PRG001");
        course.setId(10L);

        lecturer.setDepartment(department);
        lecturer.getCourses().add(course);

        when(lecturerRepository.findById(1L))
                .thenReturn(Optional.of(lecturer));

        // Act
        LecturerResponse response =
                lecturerService.getById(1L);

        // Assert
        assertNotNull(response);

        verify(lecturerRepository).findById(1L);
    }
}
