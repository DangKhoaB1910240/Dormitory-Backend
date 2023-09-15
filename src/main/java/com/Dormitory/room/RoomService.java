package com.Dormitory.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.NotFoundException;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;

    public Room findRoomById(Integer id) {
        if(roomRepository.findById(id).isPresent()) {
            return roomRepository.findById(id).get();
        }
        throw new NotFoundException("Phòng không tồn tại với id + "+id);
    }
}
