package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.StudentRequestDTO;
import com.example.studentmanagement.dto.StudentResponseDTO;
import com.example.studentmanagement.dto.StudentUpdateDTO;
import com.example.studentmanagement.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    private StudentResponseDTO responseDTO;
    private StudentRequestDTO requestDTO;
    private StudentUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        responseDTO = StudentResponseDTO.builder()
                .id(1L)
                .name("John Doe")
                .age(20)
                .gender("MALE")
                .email("john@example.com")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        requestDTO = StudentRequestDTO.builder()
                .name("John Doe")
                .age(20)
                .gender("MALE")
                .email("john@example.com")
                .build();

        updateDTO = StudentUpdateDTO.builder()
                .name("John Updated")
                .age(21)
                .build();
    }

    @Test
    void createStudent_Success() throws Exception {
        when(studentService.createStudent(any(StudentRequestDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student created successfully"))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));

        verify(studentService, times(1)).createStudent(any(StudentRequestDTO.class));
    }

    @Test
    void createStudent_InvalidInput_ReturnsBadRequest() throws Exception {
        StudentRequestDTO invalidDTO = StudentRequestDTO.builder()
                .name("J")
                .age(200)
                .gender("INVALID")
                .email("invalid-email")
                .build();

        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllStudents_Success() throws Exception {
        List<StudentResponseDTO> students = Arrays.asList(responseDTO);
        when(studentService.getAllStudents()).thenReturn(students);

        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].name").value("John Doe"));

        verify(studentService, times(1)).getAllStudents();
    }

    @Test
    void getStudentById_Success() throws Exception {
        when(studentService.getStudentById(1L)).thenReturn(responseDTO);

        mockMvc.perform(get("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("John Doe"));

        verify(studentService, times(1)).getStudentById(1L);
    }

    @Test
    void updateStudent_Success() throws Exception {
        when(studentService.updateStudent(anyLong(), any(StudentUpdateDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(put("/api/students/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student updated successfully"));

        verify(studentService, times(1)).updateStudent(anyLong(), any(StudentUpdateDTO.class));
    }

    @Test
    void deleteStudent_Success() throws Exception {
        doNothing().when(studentService).deleteStudent(1L);

        mockMvc.perform(delete("/api/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Student deleted successfully"));

        verify(studentService, times(1)).deleteStudent(1L);
    }

    @Test
    void getStudentByEmail_Success() throws Exception {
        when(studentService.getStudentByEmail("john@example.com")).thenReturn(responseDTO);

        mockMvc.perform(get("/api/students/email/john@example.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.email").value("john@example.com"));

        verify(studentService, times(1)).getStudentByEmail("john@example.com");
    }
}
