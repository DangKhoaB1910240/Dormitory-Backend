package com.Dormitory.roomtype;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.image.Image;
import com.Dormitory.room.Room;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
@Builder
public class RoomType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Tên dãy không thể bỏ trống")
    @Column(unique = true)
    private String name;

    @NotNull(message = "Nhập số lượng sinh viên có thể ở tối đa")
    private Integer maxQuantity;

    @JsonSerialize
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomType")//ánh xạ tên biến bên Image
    private List<Image> images;

    @NotNull(message = "Gía phòng không thể bỏ trống")
    private Float price;

    @NotNull(message = "Phòng này có máy lạnh không ?")
    private Boolean isAirConditioned; // Có máy lạnh không

    @NotNull(message = "Phòng này cho phép nấu ăn không ?")
    private Boolean isCooked; // Có nấu ăn không

    private Boolean enable = true;

    private LocalDate createdDate = LocalDate.now();

    private LocalDate updatedDate = LocalDate.now();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "roomType") // Một RoomType có nhiều Room, ánh xạ đến trường roomType trong lớp Room
    private List<Room> rooms;
    
    public RoomType(String name, Integer maxQuantity, Float price, Boolean isAirConditioned,
            Boolean isCooked) {
        this.name = name;
        this.maxQuantity = maxQuantity;
        this.price = price;
        this.isAirConditioned = isAirConditioned;
        this.isCooked = isCooked;
    }

}
