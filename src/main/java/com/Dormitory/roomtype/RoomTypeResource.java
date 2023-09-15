package com.Dormitory.roomtype;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Dormitory.image.ImageService;

@RestController
@RequestMapping("api/v1/roomtype")
public class RoomTypeResource {
    
    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeResource(RoomTypeService roomTypeService, ImageService imageService) {
        this.roomTypeService = roomTypeService;
    }

    @PostMapping()
    public ResponseEntity<?> addRoomType(@RequestBody RoomType roomType) {
        roomTypeService.addRoomType(roomType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }



}
