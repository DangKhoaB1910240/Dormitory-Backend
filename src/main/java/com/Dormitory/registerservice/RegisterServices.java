package com.Dormitory.registerservice;

import java.time.LocalDate;

import com.Dormitory.service.Services;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RegisterServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate registrationDate=LocalDate.now();
    @NotNull(message = "ID của student bị null")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;  // Đối tượng Student đại diện cho sinh viên đã đăng ký
    @NotNull(message = "ID của service bị null")
    @ManyToOne
    @JoinColumn(name = "service_id")
    private Services service;  // Đối tượng Services đại diện cho dịch vụ đã đăng ký
    @ManyToOne
    @JoinColumn(name = "sesmester_id")
    private Sesmester sesmester;  // Đối tượng Services đại diện cho dịch vụ đã đăng ký
    private Float price;
    private Integer status = 0;
}