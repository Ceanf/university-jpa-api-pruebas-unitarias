package com.example.universityjpa.service;

import com.example.universityjpa.dto.DepartmentResponse;
import com.example.universityjpa.entity.Department;
import com.example.universityjpa.entity.Lecturer;
import com.example.universityjpa.entity.Student;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.DepartmentRepository;
import com.example.universityjpa.repository.LecturerRepository;
import com.example.universityjpa.repository.StudentRepository;

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
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private LecturerRepository lecturerRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void create_deberiaCrearDepartamentoCorrectamente() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        when(departmentRepository.save(any(Department.class)))
                .thenReturn(department);

        DepartmentResponse response =
                departmentService.create("Ingenieria");

        assertNotNull(response);

        verify(departmentRepository).save(any(Department.class));
    }

    @Test
    void getAll_deberiaRetornarTodosLosDepartamentos() {
        Department department1 = new Department("Ingenieria");
        department1.setId(1L);

        Department department2 = new Department("Medicina");
        department2.setId(2L);

        when(departmentRepository.findAll())
                .thenReturn(List.of(department1, department2));

        DepartmentResponse[] responses =
                departmentService.getAll();

        assertNotNull(responses);
        assertEquals(2, responses.length);

        verify(departmentRepository).findAll();
    }

    @Test
    void getAll_deberiaRetornarArregloVacio() {
        when(departmentRepository.findAll())
                .thenReturn(List.of());

        DepartmentResponse[] responses =
                departmentService.getAll();

        assertNotNull(responses);
        assertEquals(0, responses.length);

        verify(departmentRepository).findAll();
    }

    @Test
    void getById_deberiaRetornarDepartamentoCuandoExiste() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        DepartmentResponse response =
                departmentService.getById(1L);

        assertNotNull(response);

        verify(departmentRepository).findById(1L);
    }

    @Test
    void getById_deberiaLanzarExcepcionCuandoNoExiste() {
        when(departmentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.getById(99L)
        );

        verify(departmentRepository).findById(99L);
    }

    @Test
    void delete_deberiaEliminarDepartamentoCuandoExiste() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        departmentService.delete(1L);

        verify(departmentRepository).findById(1L);
        verify(departmentRepository).delete(department);
    }

    @Test
    void delete_deberiaLanzarExcepcionCuandoNoExiste() {
        when(departmentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.delete(99L)
        );

        verify(departmentRepository, never())
                .delete(any(Department.class));
    }

    @Test
    void addLecturer_deberiaAgregarDocenteAlDepartamento() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        Lecturer lecturer = mock(Lecturer.class);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(lecturerRepository.findById(10L))
                .thenReturn(Optional.of(lecturer));

        DepartmentResponse response =
                departmentService.addLecturer(1L, 10L);

        assertNotNull(response);

        verify(lecturer).setDepartment(department);
        verify(lecturerRepository).save(lecturer);
        verify(departmentRepository).findById(1L);
        verify(lecturerRepository).findById(10L);
    }

    @Test
    void addLecturer_llamarDosVecesConElMismoDocenteLoDuplicaEnLaLista() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        Lecturer lecturer = mock(Lecturer.class);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(lecturerRepository.findById(10L))
                .thenReturn(Optional.of(lecturer));

        departmentService.addLecturer(1L, 10L);
        departmentService.addLecturer(1L, 10L);

        // department.getLecturers() es un List, no un Set: a diferencia de
        // CourseService.enrollStudent (que usa Set y no duplica), aqui llamar
        // dos veces con el mismo docente lo agrega dos veces.
        assertEquals(2, department.getLecturers().size());
    }

    @Test
    void addLecturer_deberiaLanzarExcepcionSiNoExisteElDocente() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(lecturerRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.addLecturer(1L, 99L)
        );

        verify(lecturerRepository, never())
                .save(any(Lecturer.class));
    }

    @Test
    void addStudent_deberiaAgregarEstudianteAlDepartamento() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        Student student = mock(Student.class);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(studentRepository.findById(10L))
                .thenReturn(Optional.of(student));

        DepartmentResponse response =
                departmentService.addStudent(1L, 10L);

        assertNotNull(response);

        verify(student).setDepartment(department);
        verify(studentRepository).save(student);
        verify(departmentRepository).findById(1L);
        verify(studentRepository).findById(10L);
    }

    @Test
    void addStudent_deberiaLanzarExcepcionSiNoExisteElEstudiante() {
        Department department = new Department("Ingenieria");
        department.setId(1L);

        when(departmentRepository.findById(1L))
                .thenReturn(Optional.of(department));

        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> departmentService.addStudent(1L, 99L)
        );

        verify(studentRepository, never())
                .save(any(Student.class));
    }
}