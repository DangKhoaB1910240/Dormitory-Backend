package com.Dormitory.admin;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Dormitory.student.Student;
import com.Dormitory.student.StudentResponseDTO;
import com.Dormitory.student.StudentService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/admin")
public class AdminResource {
    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<Admin> getAdminByNoAdmin(@RequestParam("numberAdmin") String numberAdmin) {
        Admin admin = adminService.getAdminByNoAdmin(numberAdmin);
        return ResponseEntity.status(HttpStatus.OK).body(admin);
    }

    
}
