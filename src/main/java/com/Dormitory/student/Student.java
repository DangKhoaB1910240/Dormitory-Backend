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
    @NotEmpty(message = "Username cannot be empty")
    private String numberStudent;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email invalid")
    private String email;

    @NotEmpty(message = "Phone cannot be empty")
    private String phone;

    @NotEmpty(message = "Major cannot be empty")
    private String major;

    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Integer gender;//1 -> boy, 0-> girl

    @NotEmpty(message = "Classroom cannot be empty")
    private String classroom;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
