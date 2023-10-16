package com.Dormitory.bill;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Optional<Bill> findFirstByRoomTypeAndNumberRoomOrderByCreatedDateDesc(String roomType, Integer numberRoom);
    List<Bill> findByRoomTypeAndNumberRoomOrderByCreatedDateDesc(String roomType, Integer numberRoom);
}
