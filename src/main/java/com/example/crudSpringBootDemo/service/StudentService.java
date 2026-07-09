package com.example.crudSpringBootDemo.service;

import com.example.crudSpringBootDemo.dto.CreateStudentRequestDTO;
import com.example.crudSpringBootDemo.dto.CreateStudentResponseDto;
import com.example.crudSpringBootDemo.dto.UpdateStudentRequestDto;
import com.example.crudSpringBootDemo.dto.UpdateStudentResponseDto;
import com.example.crudSpringBootDemo.entity.Student;
import com.example.crudSpringBootDemo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
private StudentRepository studentRepository;

public StudentService(StudentRepository studentRepository){
    this.studentRepository = studentRepository;
}
    public CreateStudentResponseDto createStudent(CreateStudentRequestDTO createStudentRequestDTO){
   Student student = mapToEntity(createStudentRequestDTO);

   student.setCreatedAt(LocalDateTime.now());
   student.setUpdatedAt(LocalDateTime.now());

   Student studentResp = studentRepository.save(student);

   return mapToDto(studentResp);
    }

    public CreateStudentResponseDto getStudent(Long id){
   Optional<Student> studentResp = studentRepository.findByIdAndDeletedIsFalse(id);

   if (studentResp.isPresent()){
       return mapToDto(studentResp.get());
   }
   return null;
    }

    public List<CreateStudentResponseDto> getAllStudent(){
    List<Student> studentList = studentRepository.findByDeletedIsFalse();

    return studentList.stream()
            .map(this::mapToDto)
            .toList();
    }

    public UpdateStudentResponseDto updateStudent(Long id, UpdateStudentRequestDto studentReq){
    Optional<Student> existingStudent =
            studentRepository.findByIdAndDeletedIsFalse(id);

   if (existingStudent.isEmpty()){
       return  null;
           }
        Student studentToSave  = existingStudent.get();
           studentToSave.setName(studentReq.getName());

           studentToSave.setRollNo(studentReq.getRollNo());
                studentToSave.setSubject(studentReq.getSubject());

           studentToSave.setAge(studentReq.getAge());
            studentToSave.setUpdatedAt(LocalDateTime.now());

   Student savedStudent = studentRepository.save(studentToSave);

   return mapToUpdateDto(savedStudent);
    }
public Boolean deleteStudent(Long id){
    Boolean isStudent = studentRepository.existsById(id);

    if (!isStudent) return  false;

    studentRepository.deleteById(id);
    return  true;
}

public Boolean deleteStudentSoftly(Long id){
    Optional<Student> existingStudent =
            studentRepository.findByIdAndDeletedIsFalse(id);

    if (existingStudent.isEmpty()){
        return false;
    }
    Student studentToSave = existingStudent.get();

    studentToSave.setDeleted(true);

    studentRepository.save(studentToSave);
    return  true;
}

private Student mapToEntity(CreateStudentRequestDTO studentReqDto){
    Student student = new Student();
    student.setName(studentReqDto.getName());
    student.setAge(studentReqDto.getAge());
    student.setEmail(studentReqDto.getEmail());
    student.setRollNo(studentReqDto.getRollNo());
    student.setSubject(studentReqDto.getSubject());

    student.setDeleted(false);
    return student;
}

private CreateStudentResponseDto mapToDto(Student student){
    CreateStudentResponseDto responseDto = new CreateStudentResponseDto();

    responseDto.setId(student.getId());
    responseDto.setAge(student.getAge());
    responseDto.setEmail(student.getEmail());
    responseDto.setName(student.getName());
    responseDto.setRollNo(student.getRollNo());
    responseDto.setSubject(student.getSubject());
    responseDto.setMessage("Student saved successfully");
    responseDto.setCreatedAt(student.getCreatedAt());
    responseDto.setUpdatedAt(student.getUpdatedAt());
    return responseDto;
}

private UpdateStudentResponseDto mapToUpdateDto(Student student){
    UpdateStudentResponseDto responseDto = new UpdateStudentResponseDto();
    responseDto.setId(student.getId());
    responseDto.setAge(student.getAge());
    responseDto.setEmail(student.getEmail());
    responseDto.setName(student.getName());
    responseDto.setRollNo(student.getRollNo());
    responseDto.setSubject(student.getSubject());
    responseDto.setMessage("Student updated successfully");

    responseDto.setUpdatedAt(student.getUpdatedAt());
    return responseDto;

}
}
