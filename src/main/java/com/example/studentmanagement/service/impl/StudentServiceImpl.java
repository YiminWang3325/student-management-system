package com.example.studentmanagement.service.impl;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentUpdateDTO;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.DuplicateResourceException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public StudentResponseDTO createStudent(StudentRequestDTO requestDTO) {
        // Check if email already exists
        if (studentRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Student with email " + requestDTO.getEmail() + " already exists");
        }

        // Create student entity
        Student student = Student.builder()
                .name(requestDTO.getName())
                .age(requestDTO.getAge())
                .gender(requestDTO.getGender())
                .email(requestDTO.getEmail())
                .build();

        // Save and return
        Student savedStudent = studentRepository.save(student);
        return mapToResponseDTO(savedStudent);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudentResponseDTO> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        return mapToResponseDTO(student);
    }

    @Override
    @Transactional
    public StudentResponseDTO updateStudent(Long id, StudentUpdateDTO updateDTO) {
        // Find existing student
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));

        // Check email uniqueness if email is being updated
        if (updateDTO.getEmail() != null && !updateDTO.getEmail().equals(student.getEmail())) {
            if (studentRepository.existsByEmailAndIdNot(updateDTO.getEmail(), id)) {
                throw new DuplicateResourceException("Student with email " + updateDTO.getEmail() + " already exists");
            }
            student.setEmail(updateDTO.getEmail());
        }

        // Update fields if provided
        if (updateDTO.getName() != null) {
            student.setName(updateDTO.getName());
        }
        if (updateDTO.getAge() != null) {
            student.setAge(updateDTO.getAge());
        }
        if (updateDTO.getGender() != null) {
            student.setGender(updateDTO.getGender());
        }

        // Save and return
        Student updatedStudent = studentRepository.save(student);
        return mapToResponseDTO(updatedStudent);
    }

    @Override
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found with id: " + id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public StudentResponseDTO getStudentByEmail(String email) {
        Student student = studentRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with email: " + email));
        return mapToResponseDTO(student);
    }

    private StudentResponseDTO mapToResponseDTO(Student student) {
        return StudentResponseDTO.builder()
                .id(student.getId())
                .name(student.getName())
                .age(student.getAge())
                .gender(student.getGender())
                .email(student.getEmail())
                .createdAt(student.getCreatedAt())
                .updatedAt(student.getUpdatedAt())
                .build();
    }
}
