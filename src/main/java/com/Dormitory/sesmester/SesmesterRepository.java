package com.Dormitory.sesmester;

import java.time.LocalDate;
import java.util.Optional;

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
}
