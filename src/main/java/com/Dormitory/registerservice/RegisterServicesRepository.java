package com.Dormitory.registerservice;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterServicesRepository extends JpaRepository<RegisterServices, Integer> {
    Optional<RegisterServices> findBySesmesterIdAndStudentIdAndServiceId(Integer sesmesterId, Integer studentId, Integer serviceId);
    List<RegisterServices> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
    @Query("SELECT r FROM RegisterServices r " +
           "WHERE (:sesmester IS NULL OR r.sesmester.sesmester = :sesmester) " +
           "AND (:schoolYear IS NULL OR r.sesmester.schoolYear = :schoolYear) " +
           "AND (:major IS NULL OR r.student.major = :major) " +
           "AND (:numberStudent IS NULL OR r.student.numberStudent LIKE :numberStudent%) " +
           "AND (:gender IS NULL OR r.student.gender = :gender) " )
    Page<RegisterServices> findByFilter(
            @Param("sesmester") Integer sesmester,
            @Param("schoolYear") String schoolYear,
            @Param("major") String major,
            @Param("numberStudent") String numberStudent,
            @Param("gender") Integer gender,
            Pageable pageable);
            @Query("SELECT r FROM RegisterServices r WHERE (r.student.name LIKE %:name% OR r.student.numberStudent LIKE %:numberStudent%) ")
            Page<RegisterServices> searchByStudentNameOrNumber(@Param("name") String name, @Param("numberStudent") String numberStudent,Pageable pageable);
}
