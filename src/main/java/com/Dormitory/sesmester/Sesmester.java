package com.Dormitory.sesmester;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.service.Services;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class Sesmester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Không bỏ trống học kỳ")
    private Integer sesmester;
    @NotNull(message = "Không bỏ trống năm học")
    private String schoolYear;
    @NotNull(message = "Không bỏ ngày bắt đầu")
    private LocalDate startDate;
    @NotNull(message = "Không bỏ ngày kết thúc ")
    private LocalDate endDate;
    @NotNull(message = "Không bỏ ngày bắt đầu đăng ký")
    private LocalDate registrationStartDate;
    @NotNull(message = "Không bỏ ngày kết thúc đăng ký")
    private LocalDate registrationEndDate;
    private Boolean status = false; //Coi học kỳ đã được mở chưa
    private Integer holidayWeek = 0;
}
