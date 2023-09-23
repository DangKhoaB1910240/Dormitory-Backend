package com.Dormitory.roomtype;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    Boolean existsByName(String name);
    
    @Query("SELECT DISTINCT rt.maxQuantity FROM RoomType rt ORDER BY rt.maxQuantity ASC")
    List<Integer> findDistinctMaxQuantitiesSortedAsc();
    //Viết một hàm để lấy ra RoomTypeResponseDTO 
    // Có máy lạnh ? Có cho nấu ăn ? số lượng ? và giá 

    List<RoomType> findByMaxQuantity(Integer maxQuantity);
    List<RoomType> findByIsCooked(Boolean isCooked);
    List<RoomType> findByIsAirConditioned(Boolean isAirConditioned);

    List<RoomType> findByMaxQuantityAndIsCooked(
        Integer maxQuantity,
        Boolean isCooked
    );
    List<RoomType> findByMaxQuantityAndIsAirConditioned(
        Integer maxQuantity,
        Boolean isAirConditioned
    );
    List<RoomType> findByIsCookedAndIsAirConditioned(
        Boolean isCooked, 
        Boolean isAirConditioned
    );

    List<RoomType> findByMaxQuantityAndIsCookedAndIsAirConditioned(
        Integer maxQuantity,
        Boolean isCooked, 
        Boolean isAirConditioned
    );
}

