package com.Dormitory.roomtype;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.image.Image;

import jakarta.transaction.Transactional;

@Service
public class RoomTypeService {
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    
    @Transactional
    public void addRoomType(RoomType roomType) {    
        if(roomTypeRepository.existsByName(roomType.getName())) {
            throw new AlreadyExistsException("Tên loại phòng đã bị trùng");
        }
        roomTypeRepository.save(roomType);
    }
    public void updateRoomType(Integer id, RoomType roomType) {
        RoomType r = roomTypeRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tìm thấy loại phòng này"));
        r.setEnable(roomType.getEnable());
        roomTypeRepository.save(r);
    }
    public RoomTypeResponseDTO getRoomTypeById(Integer id) {
        Optional<RoomType> roomType = roomTypeRepository.findById(id);
        RoomTypeResponseDTO roomTypeResponseDTO = new RoomTypeResponseDTO();
        if(roomType.isPresent()) {
            roomTypeResponseDTO = convertRoomTypeToResponseDTO(roomType.get());
            return roomTypeResponseDTO;
        }else {
            throw new NotFoundException("Loại phòng không tồn tại với id: "+id);
        }
        
    }

    public List<RoomTypeResponseDTO> getAllRoomType() {
        List<RoomType> roomTypes = roomTypeRepository.findAllByOrderByNameAsc();
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }


    public List<Integer> getRoomTypeQuantity() {
        return roomTypeRepository.findDistinctMaxQuantitiesSortedAsc();
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByMaxQuantity(Integer maxQuantity) {
        List<RoomType> roomTypes = roomTypeRepository.findByMaxQuantity(maxQuantity);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByIsCooked(Boolean isCooked) {
        List<RoomType> roomTypes = roomTypeRepository.findByIsCooked(isCooked);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByIsAirConditioned(Boolean isAirConditioned) {
        List<RoomType> roomTypes = roomTypeRepository.findByIsAirConditioned(isAirConditioned);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByMaxQuantityAndIsCooked(Integer maxQuantity,Boolean isCooked) {
        List<RoomType> roomTypes = roomTypeRepository.findByMaxQuantityAndIsCooked(maxQuantity, isCooked);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByMaxQuantityAndIsAirConditioned(Integer maxQuantity,Boolean isAirConditioned) {
        List<RoomType> roomTypes = roomTypeRepository.findByMaxQuantityAndIsAirConditioned(maxQuantity, isAirConditioned);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByIsCookedAndIsAirConditioned(Boolean isCooked,Boolean isAirConditioned) {
        List<RoomType> roomTypes = roomTypeRepository.findByIsCookedAndIsAirConditioned(isCooked, isAirConditioned);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    public List<RoomTypeResponseDTO> getAllRoomTypeByMaxQuantityAndIsCookedAndIsAirConditioned(Integer maxQuantity,Boolean isCooked,Boolean isAirConditioned) {
        List<RoomType> roomTypes = roomTypeRepository.findByMaxQuantityAndIsCookedAndIsAirConditioned(maxQuantity, isCooked, isAirConditioned);
        return convertListRoomTypeToListResponseDTO(roomTypes);
    }

    private List<RoomTypeResponseDTO> convertListRoomTypeToListResponseDTO(List<RoomType> roomTypes) {
        List<RoomTypeResponseDTO> responses = new ArrayList<>();
        for(RoomType roomType: roomTypes) {
            RoomTypeResponseDTO response = new RoomTypeResponseDTO();
            response.setId(roomType.getId());
            response.setName(roomType.getName());
            response.setMaxQuantity(roomType.getMaxQuantity());
            response.setImages(roomType.getImages());
            response.setPrice(roomType.getPrice());
            response.setIsAirConditioned(roomType.getIsAirConditioned());
            response.setIsCooked(roomType.getIsCooked());
            response.setEnable(roomType.getEnable());
            response.setCreatedDate(roomType.getCreatedDate());
            response.setUpdatedDate(roomType.getUpdatedDate());
            responses.add(response);
        }
        
        return responses;
    }
    private RoomTypeResponseDTO convertRoomTypeToResponseDTO(RoomType roomType) {
            RoomTypeResponseDTO response = new RoomTypeResponseDTO();
            response.setId(roomType.getId());
            response.setName(roomType.getName());
            response.setMaxQuantity(roomType.getMaxQuantity());
            response.setImages(roomType.getImages());
            response.setPrice(roomType.getPrice());
            response.setIsAirConditioned(roomType.getIsAirConditioned());
            response.setIsCooked(roomType.getIsCooked());
            response.setEnable(roomType.getEnable());
            response.setCreatedDate(roomType.getCreatedDate());
            response.setUpdatedDate(roomType.getUpdatedDate());
        return response;
    }

}
