package com.example.universityjpa.service;

import com.example.universityjpa.dto.StudentResponse;
import com.example.universityjpa.entity.Course;
import com.example.universityjpa.entity.Department;
import com.example.universityjpa.entity.Student;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.StudentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @Test
    void create_deberiaCrearEstudianteCorrectamente() {
        // Arrange
        Student student = new Student("Ana Lopez");
        student.setId(1L);

        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        // Act
        StudentResponse response =
                studentService.create("Ana Lopez");

        // Assert
        assertNotNull(response);

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void getAll_deberiaRetornarTodosLosEstudiantes() {
        // Arrange
        Student student1 = new Student("Ana Lopez");
        student1.setId(1L);

        Student student2 = new Student("Carlos Perez");
        student2.setId(2L);

        when(studentRepository.findAll())
                .thenReturn(List.of(student1, student2));

        // Act
        StudentResponse[] responses =
                studentService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.length);

        verify(studentRepository).findAll();
    }

    @Test
    void getAll_deberiaRetornarArregloVacio() {
        // Arrange
        when(studentRepository.findAll())
                .thenReturn(List.of());

        // Act
        StudentResponse[] responses =
                studentService.getAll();

        // Assert
        assertNotNull(responses);
        assertEquals(0, responses.length);

        verify(studentRepository).findAll();
    }

    @Test
    void getById_deberiaRetornarEstudianteCuandoExiste() {
        // Arrange
        Student student = new Student("Ana Lopez");
        student.setId(1L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        // Act
        StudentResponse response =
                studentService.getById(1L);

        // Assert
        assertNotNull(response);

        verify(studentRepository).findById(1L);
    }

    @Test
    void getById_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.getById(99L)
        );

        verify(studentRepository).findById(99L);
    }

    @Test
    void delete_deberiaEliminarEstudianteCuandoExiste() {
        // Arrange
        Student student = new Student("Ana Lopez");
        student.setId(1L);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        // Act
        assertDoesNotThrow(() -> studentService.delete(1L));

        // Assert
        verify(studentRepository).findById(1L);
        verify(studentRepository).delete(student);
    }

    @Test
    void delete_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> studentService.delete(99L)
        );

        verify(studentRepository, never())
                .delete(any(Student.class));
    }

    @Test
    void getById_deberiaConvertirDepartamentoYCurso() {
        // Arrange
        Student student = new Student("Ana Lopez");
        student.setId(1L);

        Department department = new Department("Ingenieria");
        department.setId(5L);

        Course course = new Course("Programacion", "PRG001");
        course.setId(10L);

        student.setDepartment(department);
        student.getCourses().add(course);

        when(studentRepository.findById(1L))
                .thenReturn(Optional.of(student));

        // Act
        StudentResponse response =
                studentService.getById(1L);

        // Assert
        assertNotNull(response);

        verify(studentRepository).findById(1L);
    }
}
