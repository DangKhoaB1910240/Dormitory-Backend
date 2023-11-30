package com.Dormitory.student;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class Student {
    
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @Column(unique = true)
    private String numberStudent;

    private String name;
    
    private String address;
    @Email(message = "Email invalid")
    private String email;
    private String phone;

    private String major;

    private LocalDate birthday;
    private Integer gender;//1 -> boy, 0-> girl
    private String classroom;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    private Integer status = 0;
    //0 -> ổn
    //1 -> ra trường rồi
    //2 -> danh sách đen
    //3 -> danh sách đen tự tạow
}
