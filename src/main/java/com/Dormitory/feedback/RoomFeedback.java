package com.Dormitory.feedback;

import java.time.LocalDate;

import com.Dormitory.material.Material;
import com.Dormitory.room.Room;
import com.Dormitory.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor 
@AllArgsConstructor
@Entity // Tạo bảng trong CSDL
public class RoomFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @JoinColumn(name = "student_id")
    @ManyToOne
    private Student student; //Sinh viên nào gửi
    @JoinColumn(name = "material_id")
    @ManyToOne
    private Material material; //phản hồi CSVC nào
    @JoinColumn(name = "room_id")
    @ManyToOne
    private Room room;// Thuộc phòng nào
    private LocalDate sendDate = LocalDate.now();
    private Boolean status = false;
    private String note = new String();

}
