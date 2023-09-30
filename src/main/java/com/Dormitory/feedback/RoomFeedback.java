// package com.Dormitory.feedback;

// import java.time.LocalDate;

// import com.Dormitory.admin.Admin;
// import com.Dormitory.material.Material;
// import com.Dormitory.room.Room;
// import com.Dormitory.sesmester.Sesmester;
// import com.Dormitory.student.Student;

// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import jakarta.validation.constraints.NotNull;
// import lombok.AllArgsConstructor;
// import lombok.Data;
// import lombok.NoArgsConstructor;

// @Data
// @NoArgsConstructor 
// @AllArgsConstructor
// @Entity // Tạo bảng trong CSDL
// public class RoomFeedback {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer id;

//     @JoinColumn(name = "student_id")
//     @ManyToOne
//     private Student student; //Sinh viên nào gửi

//     @JoinColumn(name = "material_id")
//     @ManyToOne
//     private Material material; //phản hồi CSVC nào

//     @JoinColumn(name = "sesmester_id")
//     @ManyToOne
//     private Sesmester sesmester;
//     @NotNull(message = "Cần phải nhập số lượng")
//     private Integer quantity;
//     private String noteFromStudent = "";

//     @JoinColumn(name = "room_id")
//     @ManyToOne
//     private Room room;// Thuộc phòng nào
    
//     private LocalDate sendDate = LocalDate.now();
//     private Integer status = 0;
 
//     @JoinColumn(name = "admin_id") // Thay đổi tên cột trong cơ sở dữ liệu nếu cần
//     @ManyToOne(optional = true) // optional được đặt thành true để cho phép null
//     private Admin admin; // Admin duyệt phản hồi này, có thể null
//     private String noteFromAdmin = new String();
//     private LocalDate responseDate = null;
// }
