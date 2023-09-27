package com.Dormitory.service;

import com.Dormitory.sesmester.Sesmester;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Tên dịch vụ không được rỗng")
    private String name;
    @NotEmpty(message = "Mô tả dịch vụ không được rỗng")
    private String description;
    @NotNull(message = "Giá dịch vụ không được null")
    private Float price;
    private Boolean enable = true;
    @ManyToOne
    @JoinColumn(name = "sesmester_id")
    private Sesmester sesmester;
}
