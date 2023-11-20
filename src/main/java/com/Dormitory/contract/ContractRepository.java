package com.Dormitory.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Dormitory.student.Student;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
    List<Contract> findByRoomTypeAndNumberRoomAndSesmesterId(String roomType, Integer numberRoom, Integer semesterId);
    List<Contract> findByStatus(Integer status);
    List<Contract> findAll(Sort sort);
    Page<Contract> findAll(Pageable pageable);
}
