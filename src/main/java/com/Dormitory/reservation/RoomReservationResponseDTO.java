package com.Dormitory.reservation;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationResponseDTO {
    private Integer id;
    private String numberStudent; //MSSV
    private String name; //Họ tên
    private String email; 
    private String phone;
    private String roomTypeName;
    private Integer numberRoom;
    private String bookingDateTime;
    private Boolean status;
    private String note; 
}
