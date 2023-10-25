package com.Dormitory.feedback;

import java.time.LocalDate;

import com.Dormitory.admin.Admin;
import com.Dormitory.student.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackRequestDTO {
    private Student student;
    private String roomType;
    private Integer numberRoom;
    private LocalDate editDate;
    private Integer adminId;
    private Integer status;
}
