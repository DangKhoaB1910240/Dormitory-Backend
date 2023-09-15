package com.Dormitory.reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomReservationRequestDTO {
    @NotNull(message = "Sesmester id cannot be null")
    private Integer sesmester_id;
    
    @NotNull(message = "Student id cannot be null")
    private Integer student_id;

    @NotNull(message = "Room id cannot be null")
    private Integer room_id;
}
