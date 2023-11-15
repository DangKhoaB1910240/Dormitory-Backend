package com.Dormitory.bill;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Optional<Bill> findFirstByRoomTypeAndNumberRoomOrderByCreatedDateDesc(String roomType, Integer numberRoom);
    List<Bill> findByRoomTypeAndNumberRoomOrderByCreatedDateDesc(String roomType, Integer numberRoom);
    @Query("SELECT b FROM Bill b ORDER BY b.createdDate DESC, b.roomType ASC, b.numberRoom ASC")
    Page<Bill> findAllBillsSorted(Pageable pageable);
     // Lấy danh sách hóa đơn theo tháng, năm và roomType
     @Query("SELECT b FROM Bill b WHERE MONTH(b.createdDate) = :month AND YEAR(b.createdDate) = :year ORDER BY b.roomType ASC")
    List<Bill> findByCreatedDateMonthAndCreatedDateYear(
            @Param("month") int month,
            @Param("year") int year
    );
}
