package com.Dormitory.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
public class StudentResource {
    
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<StudentResponseDTO> getStudentByNoStudent(@RequestParam("numberStudent") String numberStudent) {
        Optional<Student> student = studentService.getStudentByNoStudent(numberStudent);
        
        if(student.isPresent()) {
            StudentResponseDTO studentResponseDTO = new StudentResponseDTO(
            student.get().getId(),
            student.get().getNumberStudent(),
            student.get().getName(),
            student.get().getAddress(),
            student.get().getEmail(),
            student.get().getPhone(),
            student.get().getMajor(),
            student.get().getBirthday(),
            student.get().getGender(),
             student.get().getClassroom()) ;
            return ResponseEntity.status(HttpStatus.OK).body(studentResponseDTO);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
