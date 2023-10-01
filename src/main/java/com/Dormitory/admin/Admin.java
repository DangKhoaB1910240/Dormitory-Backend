package com.Dormitory.admin;

import java.time.LocalDate;

import com.Dormitory.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
public class Admin {
    
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;

    @Column(unique = true)
    @NotEmpty(message = "Username cannot be empty")
    private String numberAdmin;

    @NotEmpty(message = "Name cannot be empty")
    private String name;
    
    @NotEmpty(message = "Address cannot be empty")
    private String address;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email invalid")
    private String email;

    @NotEmpty(message = "Phone cannot be empty")
    private String phone;

    @NotNull(message = "birthday cannot be null")
    private LocalDate birthday;

    @NotNull(message = "Gender cannot be null")
    private Integer gender;
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
