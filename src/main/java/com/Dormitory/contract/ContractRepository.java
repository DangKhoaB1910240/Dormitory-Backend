package com.Dormitory.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dormitory.student.Student;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
    List<Contract> findByRoomTypeAndNumberRoomAndSesmesterId(String roomType, Integer numberRoom, Integer semesterId);
}
