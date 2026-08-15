package com.example.universityjpa.service;

import com.example.universityjpa.dto.*;
import com.example.universityjpa.entity.Department;
import com.example.universityjpa.entity.Lecturer;
import com.example.universityjpa.entity.Student;
import com.example.universityjpa.exception.ResourceNotFoundException;
import com.example.universityjpa.repository.DepartmentRepository;
import com.example.universityjpa.repository.LecturerRepository;
import com.example.universityjpa.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final LecturerRepository lecturerRepository;
    private final StudentRepository studentRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                              LecturerRepository lecturerRepository,
                              StudentRepository studentRepository) {
        this.departmentRepository = departmentRepository;
        this.lecturerRepository = lecturerRepository;
        this.studentRepository = studentRepository;
    }

    @Transactional
    public DepartmentResponse create(String name) {
        Department department = departmentRepository.save(new Department(name));
        return toResponse(department);
    }

    @Transactional(readOnly = true)
    public DepartmentResponse[] getAll() {
        Department[] departments = departmentRepository.findAll().toArray(new Department[0]);
        DepartmentResponse[] responses = new DepartmentResponse[departments.length];
        for (int i = 0; i < departments.length; i++) {
            responses[i] = toResponse(departments[i]);
        }
        return responses;
    }

    @Transactional(readOnly = true)
    public DepartmentResponse getById(Long id) {
        return toResponse(findEntity(id));
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.delete(findEntity(id));
    }

    // Department -> Lecturers
    @Transactional
    public DepartmentResponse addLecturer(Long departmentId, Long lecturerId) {
        Department department = findEntity(departmentId);
        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new ResourceNotFoundException("Lecturer not found with id " + lecturerId));
        lecturer.setDepartment(department);
        department.getLecturers().add(lecturer);
        lecturerRepository.save(lecturer);
        return toResponse(department);
    }

    // Department -> Students
    @Transactional
    public DepartmentResponse addStudent(Long departmentId, Long studentId) {
        Department department = findEntity(departmentId);
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
        student.setDepartment(department);
        department.getStudents().add(student);
        studentRepository.save(student);
        return toResponse(department);
    }

    private Department findEntity(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    private DepartmentResponse toResponse(Department department) {
        LecturerSummary[] lecturers = department.getLecturers().stream()
                .map(l -> new LecturerSummary(l.getId(), l.getName()))
                .toArray(LecturerSummary[]::new);

        StudentSummary[] students = department.getStudents().stream()
                .map(s -> new StudentSummary(s.getId(), s.getName()))
                .toArray(StudentSummary[]::new);

        return new DepartmentResponse(department.getId(), department.getName(), lecturers, students);
    }
}
