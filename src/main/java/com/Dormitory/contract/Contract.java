package com.Dormitory.contract;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.Dormitory.admin.Admin;
import com.Dormitory.role.Role;
import com.Dormitory.service.Services;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contract {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;
    
    @NotNull(message = "student_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student; //Sinh viên nào gửi

    @NotNull(message = "sesmester_id cannot be null")
    @ManyToOne
    @JoinColumn(name = "sesmester_id")
    private Sesmester sesmester;

    private Float totalPrice;
    @NotEmpty(message = "roomType cannot be empty")
    private String roomType;
    @NotNull(message = "numberRoom cannot be null")
    private Integer numberRoom;
    private Integer status = 0;
}
