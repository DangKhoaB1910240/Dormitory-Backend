package com.Dormitory.reservation;

import java.time.LocalDateTime;

import com.Dormitory.room.Room;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationResponseDTO {
    private Integer id;
    private LocalDateTime bookingDateTime;
    private Integer status;
    private String note; 
    private Sesmester sesmester;
    private Room room;
    private RoomType roomType;
    private Student student;
}
