package com.Dormitory.contract;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Dormitory.student.Student;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    Optional<Contract> findBySesmesterIdAndStudentId(Integer sesmesterId, Integer studentId);
    List<Contract> findByRoomTypeAndNumberRoomAndSesmesterId(String roomType, Integer numberRoom, Integer sesmesterId);
    List<Contract> findByStatus(Integer status);
    List<Contract> findAll(Sort sort);
    Page<Contract> findAll(Pageable pageable);
    @Query("SELECT c FROM Contract c " +
           "WHERE (:sesmester IS NULL OR c.sesmester.sesmester = :sesmester) " +
           "AND (:schoolYear IS NULL OR c.sesmester.schoolYear = :schoolYear) " +
           "AND (:major IS NULL OR c.student.major = :major) " +
           "AND (:numberStudent IS NULL OR c.student.numberStudent LIKE :numberStudent%) " +
           "AND (:gender IS NULL OR c.student.gender = :gender) " +
           "AND c.status <> 2")
    Page<Contract> findByFilter(
            @Param("sesmester") Integer sesmester,
            @Param("schoolYear") String schoolYear,
            @Param("major") String major,
            @Param("numberStudent") String numberStudent,
            @Param("gender") Integer gender,
            Pageable pageable);
    @Query("SELECT c FROM Contract c " +
           "WHERE ((:studentName IS NULL OR c.student.name LIKE %:studentName%) " +
           "AND (:numberStudent IS NULL OR c.student.numberStudent LIKE :numberStudent%) )" +
           "AND c.status <> 2")
    Page<Contract> findBySearchFilter(
            @Param("studentName") String studentName,
            @Param("numberStudent") String numberStudent,
            Pageable pageable);
    @Query("SELECT c FROM Contract c WHERE (c.student.name LIKE %:name% OR c.student.numberStudent LIKE %:numberStudent%) AND c.status<>2")
    Page<Contract> searchByStudentNameOrNumber(@Param("name") String name, @Param("numberStudent") String numberStudent,Pageable pageable);
}
