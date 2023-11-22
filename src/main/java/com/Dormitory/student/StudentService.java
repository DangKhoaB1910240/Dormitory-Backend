package com.Dormitory.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Dormitory.contract.ContractResponseDTO;
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
    public Page<Student> getStudentGratuation(Pageable pageable) {
        Page<Student> studentPage = studentRepository.findByStatus(1, pageable);
        List<Student> students = new ArrayList<>(studentPage.getContent());
        return new PageImpl<>(students, pageable, studentPage.getTotalElements());
    }
}
