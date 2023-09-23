package com.Dormitory.sesmester;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SesmesterRepository extends JpaRepository<Sesmester, Integer> {
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Sesmester s WHERE s.id = :id AND s.startDate <= :currentDate AND s.endDate >= :currentDate")
    Boolean existsByIdAndDateRange(@Param("id") Integer id, @Param("currentDate") LocalDate currentDate);
    Sesmester findByStatus(Boolean status);
}
