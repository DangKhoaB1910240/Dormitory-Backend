package com.Dormitory.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    private Integer id;
    private String numberStudent;
    private String name;
    private Integer studentStatus;
    private String major;
    private String classroom;
    private String email;
    private String phone;
    private Integer gender;
    private String roomType;
    private Integer numberRoom;
    private Integer status;
    private Float totalPrice;
}
