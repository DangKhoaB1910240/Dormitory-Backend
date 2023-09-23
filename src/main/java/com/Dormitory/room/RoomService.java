package com.Dormitory.room;

import java.util.List;

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

    public List<Room> findByRoomType_Id(Integer id) {
        if(roomRepository.findByRoomType_Id(id).isPresent()) {
            return roomRepository.findByRoomType_Id(id).get();
        }else {
            throw new NotFoundException("Không có loại phòng phù hợp cho phòng này với id"+ id);
        }
        
    }
}
