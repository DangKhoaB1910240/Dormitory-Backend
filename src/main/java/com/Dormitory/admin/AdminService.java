package com.Dormitory.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.NotFoundException;
import com.Dormitory.student.Student;
import com.Dormitory.student.StudentRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    public Admin getAdminByNoAdmin(String noAdmin) {
        if(adminRepository.findByNumberAdmin(noAdmin).isPresent()) {
            return adminRepository.findByNumberAdmin(noAdmin).get();
        }
        throw new NotFoundException("Không tồn tại Admin với mã số: "+noAdmin);
    }
    
}
