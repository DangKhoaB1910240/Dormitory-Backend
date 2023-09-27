package com.Dormitory.reservation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/v1/reservation")
public class RoomReservationResource {
    
    @Autowired
    private RoomReservationService reservationService;

    @PostMapping()
    public ResponseEntity<?> addRoomReservation(@Valid @RequestBody RoomReservationRequestDTO roomReservationDTO) {
        reservationService.addRoomReservation(roomReservationDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("student/{noStudent}")
    public ResponseEntity<RoomReservationResponseDTO> getAllRoomReservationByNoStudentAndSesmesterIsTrue(@Valid @PathVariable("noStudent") String numberStudent) {
        return ResponseEntity.status(HttpStatus.OK).body(reservationService.getRoomReservationByNoStudentAndSesmesterIsTrue(numberStudent));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<RoomReservationResponseDTO> deleteById(@Valid @PathVariable("id") Integer roomReservationId) {
        reservationService.deleteById(roomReservationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
