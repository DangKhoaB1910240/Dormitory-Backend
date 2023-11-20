package com.Dormitory.student;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.NotFoundException;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public Optional<Student> getStudentByNoStudent(String noStudent) {
        return studentRepository.findByNumberStudent(noStudent);
    }
    public void updateStatus(String numberStudent, Integer status) {
        Student student = studentRepository.findByNumberStudent(numberStudent).orElseThrow(() -> new NotFoundException("Không tồn tại mã số sinh viên "+ numberStudent));
        student.setStatus(status);
        studentRepository.save(student);
    }

}
