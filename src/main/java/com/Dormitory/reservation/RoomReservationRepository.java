package com.Dormitory.reservation;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Dormitory.room.Room;
import com.Dormitory.sesmester.Sesmester;

@Repository
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Integer>{
    Boolean existsByStudentId(Integer id);
    List<RoomReservation> findAllByOrderByBookingDateTimeAsc();
    @Modifying
    @Query("UPDATE RoomReservation r SET r.sesmester = :sesmester, r.room = :room WHERE r.student.id = :studentId")
    void updateByStudentId(@Param("sesmester") Sesmester sesmester, @Param("room") Room room, @Param("studentId") Integer studentId);
    @Modifying
    @Query("UPDATE RoomReservation r SET r.status = true where r.id = :id")
    void updateStatusToTrueById(@Param("id") Integer id);
}
