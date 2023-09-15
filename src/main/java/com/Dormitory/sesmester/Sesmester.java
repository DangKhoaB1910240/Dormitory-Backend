package com.Dormitory.sesmester;

import java.time.LocalDate;
import java.util.List;

import com.Dormitory.reservation.RoomReservation;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class Sesmester {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer sesmester;

    private String schoolYear;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean status = false; //Coi học kỳ đã được mở chưa

    @OneToMany(mappedBy = "sesmester")
    private List<RoomReservation> roomReservations;

}