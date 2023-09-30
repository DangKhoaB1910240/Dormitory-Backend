package com.Dormitory.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Dormitory.room.Room;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomReservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private LocalDateTime bookingDateTime = LocalDateTime.now();

    private Integer status = 0;

    private String note = new String();

    @JsonSerialize
    @ManyToOne
    @JoinColumn(name = "sesmester_id")
    private Sesmester sesmester;

    @JsonSerialize
    @ManyToOne  
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
