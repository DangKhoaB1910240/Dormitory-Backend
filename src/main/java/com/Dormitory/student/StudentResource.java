package com.Dormitory.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/student")
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
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
            student.get().getClassroom(),
            student.get().getStatus()) ;
            return ResponseEntity.status(HttpStatus.OK).body(studentResponseDTO);
        }else {
            return ResponseEntity.notFound().build();
        }
    }
    @PatchMapping()
    public ResponseEntity<Void> updateStatus(@RequestParam("numberStudent") String numberStudent,@RequestParam("status") Integer status) {
        studentService.updateStatus(numberStudent,status);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("gratuation")
    public ResponseEntity<Page<Student>> getStudentGratuation(@PageableDefault(size = 2) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudentGratuation(pageable));
    }

}
