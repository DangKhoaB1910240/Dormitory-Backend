package com.Dormitory.role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //Get + Set + Equal + toString
@NoArgsConstructor //hàm xây dựng không tham số
@AllArgsConstructor //Hàm xây dựng tất cả tham số
@Entity //Tự động tạo ra bảng trong CSDL
public class Role {
    
    @Id //Đánh dấu là id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Khóa chính tăng tự động
    private Integer id;

    
    private String name;
    
}
