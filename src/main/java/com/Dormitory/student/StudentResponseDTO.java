package com.Dormitory.student;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO {
    
    private Integer id;

    private String numberStudent;

    private String name;
    
    private String address;

    private String email;

    private String phone;

    private String major;

    private String birthday;

    private Integer gender;

    private String classroom;
}
