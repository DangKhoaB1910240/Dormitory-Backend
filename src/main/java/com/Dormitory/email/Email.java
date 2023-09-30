package com.Dormitory.email;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    @NotBlank(message = "Email nhận không thể trống")
    private String toEmail; 
    @NotBlank(message = "Tiêu đề không thể trống")
    private String subject;
    @NotBlank(message = "Nội dung không thể trống")
    private String body;
}
