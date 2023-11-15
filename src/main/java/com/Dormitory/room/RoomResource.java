package com.Dormitory.room;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/room")
public class RoomResource {

    @Autowired
    private RoomService roomService;

    @GetMapping("{id}")
    public ResponseEntity<List<Room>> findByRoomType_Id(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomService.findByRoomType_Id(id));
    }
    @PatchMapping("{id}")
    public ResponseEntity<Void> updateRoom(@PathVariable("id") Integer id,@RequestBody Room room) {
        roomService.updateRoom(id,room);
        return ResponseEntity.noContent().build();
    }

}
