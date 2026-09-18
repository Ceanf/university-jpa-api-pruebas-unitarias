package com.example.universityjpa.service;

import com.example.universityjpa.dto.CourseResponse;
import com.example.universityjpa.entity.Course;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.CourseRepository;
import com.example.universityjpa.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.universityjpa.entity.Lecturer;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.example.universityjpa.entity.Student;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    void create_deberiaCrearCursoCorrectamente() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        when(courseRepository.save(any(Course.class)))
                .thenReturn(course);

        // Act
        CourseResponse response =
                courseService.create("Programacion", "PRG001");

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Programacion", response.getTitle());
        assertEquals("PRG001", response.getCode());

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void getById_deberiaRetornarCursoCuandoExiste() {
        // Arrange
        Course course = new Course("Bases de Datos", "BD001");
        course.setId(2L);

        when(courseRepository.findById(2L))
                .thenReturn(Optional.of(course));

        // Act
        CourseResponse response =
                courseService.getById(2L);

        // Assert
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("Bases de Datos", response.getTitle());
        assertEquals("BD001", response.getCode());

        verify(courseRepository).findById(2L);
    }

    @Test
    void getById_deberiaLanzarExcepcionCuandoNoExiste() {
        // Arrange
        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.getById(99L)
        );

        verify(courseRepository).findById(99L);
    }

    @Test
    void getAll_deberiaRetornarTodosLosCursos() {
        // Arrange
        Course curso1 = new Course("Programacion", "PRG001");
        curso1.setId(1L);

        Course curso2 = new Course("Bases de Datos", "BD001");
        curso2.setId(2L);

        when(courseRepository.findAll())
                .thenReturn(List.of(curso1, curso2));

        // Act
        CourseResponse[] respuestas = courseService.getAll();

        // Assert
        assertEquals(2, respuestas.length);
        assertEquals("Programacion", respuestas[0].getTitle());
        assertEquals("Bases de Datos", respuestas[1].getTitle());

        verify(courseRepository).findAll();
    }

    @Test
    void getAll_deberiaRetornarArregloVacioCuandoNoHayCursos() {
        // Arrange
        when(courseRepository.findAll())
                .thenReturn(List.of());

        // Act
        CourseResponse[] respuestas = courseService.getAll();

        // Assert
        assertEquals(0, respuestas.length);

        verify(courseRepository).findAll();
    }

    @Test
    void delete_deberiaEliminarCursoCuandoExiste() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        // Act
        assertDoesNotThrow(() -> courseService.delete(1L));

        // Assert
        verify(courseRepository).findById(1L);
        verify(courseRepository).delete(course);
    }

    @Test
    void delete_deberiaLanzarExcepcionCuandoCursoNoExiste() {
        // Arrange
        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.delete(99L)
        );

        verify(courseRepository).findById(99L);
        verify(courseRepository, never()).delete(any(Course.class));
    }

    @Test
    void enrollStudent_deberiaMatricularEstudianteCorrectamente() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        Student student = new Student();
        student.setId(10L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(studentRepository.findById(10L))
                .thenReturn(Optional.of(student));

        when(courseRepository.save(course))
                .thenReturn(course);

        // Act
        CourseResponse response =
                courseService.enrollStudent(1L, 10L);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());

        assertTrue(course.getStudents().contains(student));
        assertTrue(student.getCourses().contains(course));

        verify(courseRepository).findById(1L);
        verify(studentRepository).findById(10L);
        verify(courseRepository).save(course);
    }

    @Test
    void enrollStudent_noDeberiaDuplicarAlMatricularElMismoEstudianteDosVeces() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        Student student = new Student();
        student.setId(10L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(studentRepository.findById(10L))
                .thenReturn(Optional.of(student));

        when(courseRepository.save(course))
                .thenReturn(course);

        // Act
        courseService.enrollStudent(1L, 10L);
        courseService.enrollStudent(1L, 10L);

        // Assert
        // department.getStudents() es un Set, no un List: a diferencia de
        // DepartmentService.addLecturer (que usa List y si duplica), aqui
        // llamar dos veces con el mismo estudiante no lo agrega dos veces.
        assertEquals(1, course.getStudents().size());
        assertEquals(1, student.getCourses().size());

        verify(courseRepository, times(2)).save(course);
    }

    @Test
    void enrollStudent_deberiaLanzarExcepcionCuandoCursoNoExiste() {
        // Arrange
        when(courseRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.enrollStudent(99L, 10L)
        );

        verify(courseRepository).findById(99L);
        verify(studentRepository, never()).findById(anyLong());
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void enrollStudent_deberiaLanzarExcepcionCuandoEstudianteNoExiste() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        when(studentRepository.findById(99L))
                .thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(
                ResourceNotFoundException.class,
                () -> courseService.enrollStudent(1L, 99L)
        );

        verify(courseRepository).findById(1L);
        verify(studentRepository).findById(99L);
        verify(courseRepository, never()).save(any(Course.class));
    }

    @Test
    void getById_deberiaConvertirDocenteYEstudiantes() {
        // Arrange
        Course course = new Course("Programacion", "PRG001");
        course.setId(1L);

        Lecturer lecturer = mock(Lecturer.class);
        when(lecturer.getId()).thenReturn(5L);
        when(lecturer.getName()).thenReturn("Juan Perez");

        Student student = new Student();
        student.setId(10L);
        student.setName("Ana Lopez");

        course.setLecturer(lecturer);
        course.getStudents().add(student);

        when(courseRepository.findById(1L))
                .thenReturn(Optional.of(course));

        // Act
        CourseResponse response = courseService.getById(1L);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getLecturer());
        assertNotNull(response.getStudents());
        assertEquals(1, response.getStudents().length);

        verify(courseRepository).findById(1L);
    }
}
