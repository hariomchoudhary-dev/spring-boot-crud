package com.example.crudSpringBootDemo.controller;

import com.example.crudSpringBootDemo.dto.CreateStudentRequestDTO;
import com.example.crudSpringBootDemo.dto.CreateStudentResponseDto;
import com.example.crudSpringBootDemo.dto.UpdateStudentRequestDto;
import com.example.crudSpringBootDemo.dto.UpdateStudentResponseDto;
import com.example.crudSpringBootDemo.entity.Student;
import com.example.crudSpringBootDemo.service.StudentService;
import jakarta.validation.Valid;
import org.hibernate.sql.Update;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private StudentService studentService;

    public  StudentController(StudentService studentService){
        this.studentService = studentService;
    }
   @PostMapping
    public ResponseEntity<CreateStudentResponseDto> createStudent(
           @Valid @RequestBody CreateStudentRequestDTO createStudentRequestDTO){

CreateStudentResponseDto createdStudent = studentService.createStudent(createStudentRequestDTO);

return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(createdStudent);
    }
 @GetMapping("/{id}")
    public ResponseEntity<CreateStudentResponseDto> getStudent(@PathVariable Long id){
       CreateStudentResponseDto studentResp = studentService.getStudent(id);
       return ResponseEntity.ok(studentResp);

    }

    @GetMapping
    public ResponseEntity<List<CreateStudentResponseDto>> getAllStudent(){
       List <CreateStudentResponseDto> studentList = studentService.getAllStudent();


        return ResponseEntity.ok(studentList);

    }

    @PutMapping("/id")
    public ResponseEntity<UpdateStudentResponseDto> updateStudent(@PathVariable Long id,
                                                                          @RequestBody UpdateStudentRequestDto studentReq){
        UpdateStudentResponseDto studentResp
                = studentService.updateStudent(id, studentReq);


        return ResponseEntity.ok(studentResp);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>  deleteStudent(@PathVariable Long id){
      studentService.deleteStudent(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> deleteStudentSoftly(@PathVariable Long id){
    studentService.deleteStudentSoftly(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
