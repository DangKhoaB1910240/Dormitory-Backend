package com.Dormitory.student;

import java.time.LocalDate;

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

    private LocalDate birthday;

    private Integer gender;

    private String classroom;

    private Integer status;
}
