package com.Dormitory.bill;

import java.time.LocalDate;

import com.Dormitory.admin.Admin;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Bill {
    @Id // Đánh dấu đây là ID
    @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "admin_id")
    private Admin admin;
    private LocalDate createdDate = LocalDate.now();
    private Integer initialElectricity ;
    private Integer finalElectricity;
    private Integer initialWater;
    private Integer finalWater;
    private Float price;
    private String roomType;
    private Integer numberRoom;
    private Boolean status = false;
}
