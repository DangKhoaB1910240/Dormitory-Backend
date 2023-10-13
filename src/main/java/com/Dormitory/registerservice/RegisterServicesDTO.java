package com.Dormitory.registerservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterServicesDTO {
    private Integer id;
    private String name;
    private Float price;
    private Integer status;
}