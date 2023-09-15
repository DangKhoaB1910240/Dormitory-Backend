package com.Dormitory.contract;

import com.Dormitory.admin.Admin;
import com.Dormitory.room.Room;
import com.Dormitory.sesmester.Sesmester;
import com.Dormitory.student.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor
// @AllArgsConstructor
// @Entity // Tạo bảng trong CSDL
// public class Contract {

//     @Id // Đánh dấu đây là ID
//     @GeneratedValue(strategy = GenerationType.IDENTITY) // trường tăng tự động
//     private Integer id;
//     private Float totalPrice; // Tổng giá
//     @OneToOne
//     @JoinColumn(name = "student_id")
//     private Student Student; //Sinh viên nào
//     @ManyToOne
//     @JoinColumn(name = "room_id")
//     private Room room; //Phòng nào
//     @ManyToOne
//     @JoinColumn(name = "sesmester_id")
//     private Sesmester sesmester; //thời hạn như thế nào
//     @ManyToOne
//     @JoinColumn(name = "admin_id")
//     private Admin admin;// ai duyệt
//     private Integer isPayment;// trạng thái

// }
