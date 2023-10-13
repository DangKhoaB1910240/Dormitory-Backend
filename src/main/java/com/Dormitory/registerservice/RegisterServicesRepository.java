package com.Dormitory.registerservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterServicesRepository extends JpaRepository<RegisterServices, Integer> {
    Optional<RegisterServices> findBySesmesterIdAndStudentIdAndServiceId(Integer sesmesterId, Integer studentId, Integer serviceId);
    List<RegisterServices> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
}
