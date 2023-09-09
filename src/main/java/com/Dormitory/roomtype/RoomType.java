package com.Dormitory.roomtype;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.image.Image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class RoomType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer maxQuantity;

    @OneToMany(mappedBy = "roomType")//ánh xạ tên biến bên Image
    private List<Image> images;

    private Float price;

    private Boolean isAirConditioned;

    private Boolean isCooked;

    private Boolean enable;

    private LocalDate createdDate;

    private LocalDate updatedDate;

}
