package com.Dormitory.sesmester;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SesmesterRepository extends JpaRepository<Sesmester, Integer> {
    @Query("SELECT s FROM Sesmester s WHERE s.id = :id AND s.registrationStartDate <= :currentDate AND s.registrationEndDate >= :currentDate")
    Sesmester existsByIdAndDateRange(@Param("id") Integer id, @Param("currentDate") LocalDate currentDate);
    Sesmester findByStatus(Boolean status);
    Optional<Sesmester> findByIdAndStatus(Integer sesmesterId,Boolean status);
    @Query("SELECT s FROM Sesmester s " +
           "WHERE :currentDate BETWEEN s.registrationStartDate AND s.registrationEndDate")
    Optional<Sesmester> findSesmesterByCurrentDate(@Param("currentDate") LocalDate currentDate);
    @Query("SELECT s FROM Sesmester s " +
           "WHERE :currentDate BETWEEN s.startDate AND s.endDate")
    Optional<Sesmester> findSesmesterByCurrentDateBetweenStartDateAndEndDate(@Param("currentDate") LocalDate currentDate);
    @Query("SELECT s FROM Sesmester s " +
           "WHERE :currentDate BETWEEN :startDate AND :endDate")
    Optional<Sesmester> findSesmesterByCurrentDateBetweenStartDateAndEndDate2(
    @Param("currentDate") LocalDate currentDate,
    @Param("startDate") LocalDate startDate,
     @Param("endDate") LocalDate endDate
    );
       List<Sesmester> findAll(Sort sort);
}
