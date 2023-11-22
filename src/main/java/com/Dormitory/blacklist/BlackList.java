package com.Dormitory.blacklist;

import com.Dormitory.admin.Admin;
import com.Dormitory.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlackList {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;
    @NotNull(message = "student_id cannot be null")
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
    @NotBlank(message = "Vui lòng nhập vi phạm của sinh viên")
    private String reason = new String();
    @ManyToOne(optional = true)
    @JoinColumn(name = "admin_id")
    private Admin admin;
}
