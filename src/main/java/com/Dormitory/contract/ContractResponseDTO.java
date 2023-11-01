package com.Dormitory.contract;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractResponseDTO {
    private String numberStudent;
    private String name;
    private String major;
    private String classroom;
    private String email;
    private String phone;
    private Integer gender;
    private String roomType;
    private Integer numberRoom;
}
