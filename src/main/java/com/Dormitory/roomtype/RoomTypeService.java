package com.Dormitory.roomtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Dormitory.exception.AlreadyExistsException;

@Service
public class RoomTypeService {
    
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    public void addRoomType(RoomType roomType) {
        
        if(roomTypeRepository.existsByName(roomType.getName())) {
            throw new AlreadyExistsException("Tên loại phòng đã bị trùng");
        }
        roomTypeRepository.save(roomType);

    }

}
