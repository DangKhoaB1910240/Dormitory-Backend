package com.Dormitory.room;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity // Tạo bảng trong CSDL
// public class Room {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     private Integer numberRoom;

//     private Integer currentQuantity;

//     private Boolean enable;

//     @OneToMany
//     @JoinColumn(name = "roomtype_id", referencedColumnName = "id")
//     private RoomType roomType;

// }
