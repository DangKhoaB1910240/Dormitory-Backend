package com.Dormitory.reservation;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.Dormitory.room.Room;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

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

    private Boolean status = false;

    private String note = new String();

    @ManyToOne
    @JoinColumn(name = "sesmester_id")
    private Sesmester sesmester;

    @ManyToOne  
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
