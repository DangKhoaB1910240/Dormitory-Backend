package com.Dormitory.feedback;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseDTO {
    private Integer id;
    private String content;
    private LocalDate sendDate;
    private Integer quantity;
    private Integer status;
    private String noteFromAdmin;
}
