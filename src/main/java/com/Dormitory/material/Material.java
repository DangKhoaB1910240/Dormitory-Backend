package com.Dormitory.material;

import java.util.List;

import com.Dormitory.feedback.RoomFeedback;

import jakarta.persistence.Column;
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
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private Integer name;
    @OneToMany(mappedBy = "material")
    private List<RoomFeedback> feedbacks;
}
