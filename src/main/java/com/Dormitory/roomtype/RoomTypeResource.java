package com.Dormitory.roomtype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Dormitory.image.ImageService;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/roomtype")
public class RoomTypeResource {
    
    private RoomTypeService roomTypeService;

    @Autowired
    public RoomTypeResource(RoomTypeService roomTypeService, ImageService imageService) {
        this.roomTypeService = roomTypeService;
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> updateRoomType(@PathVariable Integer id, @RequestBody RoomType roomType) {
        roomTypeService.updateRoomType(id,roomType);
        return ResponseEntity.noContent().build();
    }

    @PostMapping()
    public ResponseEntity<?> addRoomType(@RequestBody RoomType roomType) {
        roomTypeService.addRoomType(roomType);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<RoomTypeResponseDTO> getRoomTypeById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(roomTypeService.getRoomTypeById(id));
    }

    @GetMapping()
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomType() {
        List<RoomTypeResponseDTO> roomTypeResponseDTOs = roomTypeService.getAllRoomType();
        return ResponseEntity.status(HttpStatus.OK).body(roomTypeResponseDTOs);
    }

    @GetMapping("/quantity")
    public ResponseEntity<List<Integer>> getRoomTypeQuantity() {
        List<Integer> quantitys = roomTypeService.getRoomTypeQuantity();
        return ResponseEntity.status(HttpStatus.OK).body(quantitys);
    }
    @GetMapping("max-quantity/{maxQuantity}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByMaxQuantity(@PathVariable(name = "maxQuantity") Integer maxQuantity  ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByMaxQuantity(maxQuantity);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }   
    @GetMapping("is-cooked/{isCooked}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByIsCooked(@PathVariable(name = "isCooked") Boolean isCooked  ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByIsCooked(isCooked);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }
    @GetMapping("air-conditioned/{airConditioned}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByIsAirConditioned(@PathVariable(name = "airConditioned") Boolean isAirConditioned  ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByIsAirConditioned(isAirConditioned);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }
    
    @GetMapping("max-quantity/{maxQuantity}/is-cooked/{isCooked}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByMaxQuantityAndIsCooked(
        @PathVariable(name = "maxQuantity") Integer maxQuantity,
        @PathVariable(name = "isCooked") Boolean isCooked ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByMaxQuantityAndIsCooked(maxQuantity,isCooked);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }

    @GetMapping("max-quantity/{maxQuantity}/air-conditioned/{airConditioned}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByMaxQuantityAndIsAirConditioned(
        @PathVariable(name = "maxQuantity") Integer maxQuantity,
        @PathVariable(name = "airConditioned") Boolean isAirConditioned ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByMaxQuantityAndIsAirConditioned(maxQuantity,isAirConditioned);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }

    @GetMapping("is-cooked/{isCooked}/air-conditioned/{airConditioned}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByIsCookedAndIsAirConditioned(
        @PathVariable(name = "isCooked") Boolean isCooked,
        @PathVariable(name = "airConditioned") Boolean isAirConditioned

      ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByIsCookedAndIsAirConditioned(isCooked,isAirConditioned);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }
    @GetMapping("max-quantity/{maxQuantity}/is-cooked/{isCooked}/air-conditioned/{airConditioned}")
    public ResponseEntity<List<RoomTypeResponseDTO>> getAllRoomTypeByMaxQuantityAndIsCookedAndIsAirConditioned(
        @PathVariable(name = "maxQuantity") Integer maxQuantity,
        @PathVariable(name = "isCooked") Boolean isCooked,
        @PathVariable(name = "airConditioned") Boolean isAirConditioned ) {
        List<RoomTypeResponseDTO> roomTypesResponseDTOs = roomTypeService.getAllRoomTypeByMaxQuantityAndIsCookedAndIsAirConditioned(maxQuantity,isCooked,isAirConditioned);
        return ResponseEntity.status(HttpStatus.OK).body(roomTypesResponseDTOs);
    }
}
