package com.Dormitory.roomtype;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.image.Image;
import com.Dormitory.room.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomTypeResponseDTO {
    private Integer id;
    private String name;
    private Integer maxQuantity;
    private Float price;
    private Boolean isAirConditioned; // Có máy lạnh không
    private Boolean isCooked; // Có nấu ăn không
    private Boolean enable;
    private LocalDate createdDate;
    private LocalDate updatedDate ;
    private List<Image> images;
}
