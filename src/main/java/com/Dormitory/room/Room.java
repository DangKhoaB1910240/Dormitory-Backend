package com.Dormitory.room;


import java.util.List;

import com.Dormitory.reservation.RoomReservation;
import com.Dormitory.roomtype.RoomType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class Room {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer numberRoom;

    private Integer currentQuantity;

    private Boolean enable;

    private Integer gender; //1 -> boy, 0-> girl

    @JsonIgnore
    @ManyToOne // Một Room thuộc về một RoomType
    @JoinColumn(name = "room_type_id") // Đánh dấu khóa ngoại trỏ đến RoomType
    private RoomType roomType;

    @JsonIgnore
    @OneToMany(mappedBy = "room")
    private List<RoomReservation> roomReservations;

    // @JsonIgnore
    // @OneToMany(mappedBy = "room")
    // private List<RoomFeedback> feedbacks;
}
