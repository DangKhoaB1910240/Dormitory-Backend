package com.Dormitory.bill;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://localhost:4401"})
@RequestMapping("api/v1/bill")
public class BillResource {
    @Autowired
    private BillService billService;

    @PostMapping("admin/{id}")
    public ResponseEntity<Void> addBill(@PathVariable("id") Integer id,@RequestBody @Valid BillRequestDTO requestDTO) {
        billService.addBill(id,requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping("/roomtype/{roomtype}/room/{room}")
    public ResponseEntity<List<Bill>> getByRoomTypeAndRoom(@PathVariable("roomtype") String roomType, @PathVariable("room") Integer numberRoom) {
        return ResponseEntity.status(HttpStatus.OK).body(billService.getByRoomTypeAndRoom(roomType,numberRoom));
    }
}
