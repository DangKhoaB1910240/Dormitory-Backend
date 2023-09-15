package com.Dormitory.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
    @Query("SELECT r.roomType.name FROM Room r WHERE r.id = :roomId")
    String findRoomTypeNameById(@Param("roomId") Integer roomId);
    @Query("SELECT r.numberRoom FROM Room r WHERE r.id= :roomId")
    Integer findNumberRoomById(@Param("roomId") Integer roomId);
}
