package com.Dormitory.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Dormitory.exception.AlreadyExistsException;
import com.Dormitory.exception.InvalidValueException;
import com.Dormitory.exception.NotFoundException;
import com.Dormitory.roomtype.RoomType;
import com.Dormitory.roomtype.RoomTypeRepository;

@Service
public class RoomService {
    
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    public void updateRoom(Integer id, Room room) {
        Room r = roomRepository.findById(id).orElseThrow(() -> new NotFoundException("Không tồn tại phòng với id: "+id));
        r.setEnable(room.getEnable());
        roomRepository.save(r);
    }

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
    public void createRoom( Room room) {
        RoomType roomType = roomTypeRepository.findById(room.getRoomType().getId())
        .orElseThrow(() -> new NotFoundException("Không tồn tại loại phòng này"));
        if(room.getNumberRoom() <= 0) {
            throw new InvalidValueException("Vui lòng nhập số phòng > 0");
        }
        if(roomRepository.findByNumberRoomAndRoomType_Id(room.getNumberRoom(), roomType.getId()).isPresent()) {
            throw new AlreadyExistsException("Phòng này đã tồn tại rồi");
        }
        if(room.getGender()!=0 && room.getGender()!=1) {
            throw new InvalidValueException("0 là giới tính nữ, 1 là giới tính nam");
        }
        roomRepository.save(room);
        
    }
}
