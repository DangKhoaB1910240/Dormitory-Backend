package com.Dormitory.contract;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
   
}
