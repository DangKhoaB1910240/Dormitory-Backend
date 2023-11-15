package com.Dormitory.bill;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    @GetMapping()
    public ResponseEntity<Page<Bill>> getAllBills(
        @PageableDefault(size = 2) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(billService.getAllBills(pageable));
    }
    @PatchMapping("{id}")
    public ResponseEntity<Void> updateBill(@PathVariable("id") Integer id,@RequestBody Bill bill) {
        billService.updateBill(id,bill);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("{id}")
    public ResponseEntity<Bill> getBillById(@PathVariable("id") Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(billService.getBillById(id));
    }
    @GetMapping("/statistical")
    public ResponseEntity<List<BillResponseDTO>> getStatistical(
            @RequestParam(name = "date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        List<BillResponseDTO> statisticalData = billService.getStatisticalByDate(date);
        return new ResponseEntity<>(statisticalData, HttpStatus.OK);
    }
}
