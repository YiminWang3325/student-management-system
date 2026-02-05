package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentUpdateDTO;
import com.example.studentmanagement.response.ApiResponse;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<ApiResponse<StudentResponseDTO>> createStudent(
            @Valid @RequestBody StudentRequestDTO requestDTO) {
        StudentResponseDTO student = studentService.createStudent(requestDTO);
        return new ResponseEntity<>(
                ApiResponse.success("Student created successfully", student),
                HttpStatus.CREATED
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentResponseDTO>>> getAllStudents() {
        List<StudentResponseDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(
                ApiResponse.success("Students retrieved successfully", students)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentById(@PathVariable Long id) {
        StudentResponseDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(
                ApiResponse.success("Student retrieved successfully", student)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> updateStudent(
            @PathVariable Long id,
            @Valid @RequestBody StudentUpdateDTO updateDTO) {
        StudentResponseDTO student = studentService.updateStudent(id, updateDTO);
        return ResponseEntity.ok(
                ApiResponse.success("Student updated successfully", student)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok(
                ApiResponse.success("Student deleted successfully", null)
        );
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<StudentResponseDTO>> getStudentByEmail(@PathVariable String email) {
        StudentResponseDTO student = studentService.getStudentByEmail(email);
        return ResponseEntity.ok(
                ApiResponse.success("Student retrieved successfully", student)
        );
    }
}
